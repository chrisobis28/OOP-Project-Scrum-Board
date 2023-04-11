package client.scenes;

import client.components.CardList;
import client.components.WorkspaceBoard;
import client.services.ClientServices;
import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.Cardlist;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.inject.Inject;
import java.util.*;

public class BoardViewCtrl{

    private long id;
    public ObservableList<Node> data;

    private final ServerUtils server;
    @FXML
    AnchorPane sideMenu, sideMenuClosed;
    @FXML
    ImageView menuHamburger, menuHamburgerClosed, closeButton;
    @FXML
    private FlowPane board;
    @FXML
    private FlowPane tagsPane;
    @FXML
    private Button newListButton, adminLogin;
    @FXML
    private Button refreshButton, leaveBoardButton, deleteBoardButton;
    @FXML
    private Button addTagButton;
    @FXML
    private Label boardTitle;
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TextField boardName;
    @FXML
    private Text loggedAdmin;
    @FXML
    private VBox workspace;

    private Boolean adminLoggedIn;

    private final MainCtrl mainCtrl;

    private ClientServices services;

    /**
     * Constructor for the BoardViewCtrl
     *
     * @param mainCtrl the main controller of the application.
     */
    @Inject
    public BoardViewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.services = new ClientServices(server);
        adminLoggedIn = false;
    }

    /**
     * For handling e key pressed
     *
     * @param e the event when a key is pressed
     */
    public void keyPressed(KeyEvent e) {

        // If Shift+Q is pressed, toggle the side menu
        if(Objects.requireNonNull(e.getCode()) == KeyCode.Q && e.isShiftDown())
            if(sideMenuClosed.getOpacity() == 1) {
                showMenu();
            } else {
                hideMenu();
            }
        // If ENTER is pressed while the side menu is open and the text field is not empty, try adding the board
        else if (sideMenuClosed.getOpacity() != 1 && Objects.requireNonNull(e.getCode()) == KeyCode.ENTER && !boardName.getText().isEmpty()) {
            addNewBoard(boardName.getText());
        }
        e.consume();
    }

    /**
     * Show the side menu and hide the placeholder one
     */
    public void showMenu() {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sideMenu);
        translate.setDuration(Duration.millis(150));
        translate.setToX(0);
        sideMenuClosed.setOpacity(0);
        translate.play();
    }

    public FlowPane getBoard() {
        return board;
    }

    /**
     * Hide the side menu and bring the placeholder one back
     */
    public void hideMenu(){
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sideMenu);
        translate.setDuration(Duration.millis(150));
        translate.setToX(-300);
        sideMenuClosed.setOpacity(1);
        translate.play();
    }

    /**
     * Start the process for adding a list.
     */
    public void addNewList() {
        mainCtrl.showAddList(this);
    }


    /**
     * Add a board with a given name to the repo and to the workspace.
     * @param name The name of the new Board
     */
    public WorkspaceBoard addNewBoard(String name) {
        long boardID = services.boardInRepo(name);
        if(boardID!= -1 && server.getBoardById(boardID).getIsInWorkspace())
            return null;

        WorkspaceBoard b = null;
        if (boardID == -1) {
            Board board1 = services.createNewBoard(name);
            b = initWorkspaceBoard(name, board1.getId());
        }
        else {
            Board board1 = server.getBoardById(boardID);
            if (!board1.getIsInWorkspace()) {
                b = initWorkspaceBoard(name, boardID);
                services.changeWorkspaceStateService(board1);
            }
        }
        showBoard(b);
        return b;
    }

    public WorkspaceBoard initWorkspaceBoard(String name, long savedId) {
        var b = new WorkspaceBoard(this);
        b.setBoardName(name);
        b.setId(savedId);
        workspace.getChildren().add(b);
        return b;
    }

    /**
     * Displays the elements of the new board on the view.
     *
     * @param boardToShow The board to be displayed.
     */
    public void showBoard(WorkspaceBoard boardToShow) {
        this.id = boardToShow.getBoardId();
        this.boardTitle.setText(boardToShow.getBoardName().getText());
        refreshBoard();
    }

    /**
     * Reset all the lists.
     */
    public void refreshBoard() {
        var cardlists = server.getCardLists(this.getId());

        List<Node> nodes = new ArrayList<>();
        List<Node> cardnodes = new ArrayList<>();
        ObservableList<Node> data2;
        for (var cardlist : cardlists) {
            var v = new CardList(mainCtrl, this, server, cardlist);
            List<Card> listOfCards= server.getCards(cardlist.getId());
            listOfCards.sort((a, b) -> (int) (a.getPosition() - b.getPosition()));
            long pos = 0;
            for (Card card : listOfCards) {
                if (card.getPosition() != pos) {
                    card.setPosition(pos);
                    server.editCard(card);
                }
                client.components.Card compCard = new client.components.Card(this, server, card, v);
                cardnodes.add(compCard);
                pos++;
            }
            data2 = FXCollections.observableList(cardnodes);
            v.getCards().getChildren().clear();
            v.getCards().getChildren().addAll(data2);
            data2.clear();
            nodes.add(v);
        }
        data = FXCollections.observableList(nodes);
        board.getChildren().clear();
        board.getChildren().addAll(data);
        data.clear();
    }

    /**
     * Restoring workspace.
     */
    public void initializeWorkspace() {
        workspace.getChildren().clear();
        for (Board board : server.getBoardList()) {
            if (board.getIsInWorkspace()) {
                initWorkspaceBoard(board.getBoardName(), board.getId());
            }
        }
        //add boards not in the workspace if the user is logged in as an admin
        if (this.adminLoggedIn) {
            for (Board board : server.getBoardList()) {
                if (!board.getIsInWorkspace()) {
                    initWorkspaceBoard(board.getBoardName(), board.getId());
                }
            }
        }
    }

    /**
     * Getter for the board id.
     *
     * @return the id of the board.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the id.
     *
     * @param id the id to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Show the Admin Login stage.
     */
    public void adminLogin() {
        this.mainCtrl.showAdminLogin(this);
    }

    /**
     * After logging in as an admin, show all the boards ever created in the
     * workspace and show text that confirms the login instead of the login button.
     */
    public void showOverview() {
        //Show "Logged in as admin text and hide login button"
        adminLogin.setVisible(false);
        adminLogin.setDisable(true);
        loggedAdmin.setVisible(true);
        adminLoggedIn = true;
        //Add all the boards that were not already there to the workspace
        initializeWorkspace();
    }

    /**
     * Method called at the start of showing the board view that
     * enables the admin login button again and hides the text that
     * says that the user is logged in as an admin.
     */
    public void resetAdminElements() {
        adminLoggedIn = false;
        adminLogin.setVisible(true);
        adminLogin.setDisable(false);
        loggedAdmin.setVisible(false);
    }

    /**
     * "Leaves" a board, removing it from the workspace.
     */
    public void leaveBoard() {
        Optional<ButtonType> result = this.showError("Leave Board", false);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //change the isInWorkspace field to false so that the board is included in the workspace after refreshing it
            Board board = server.getBoardById(this.getId());
            if (board.getIsInWorkspace()) {
                services.changeWorkspaceStateService(board);
                //deletes the board with this name from the workspace, not showing it anymore,
                //so we can find a new board to show
                for (Node node : workspace.getChildren()) {
                    WorkspaceBoard wboard = (WorkspaceBoard) node;
                    if (wboard.getBoardId() == this.getId()) {
                        workspace.getChildren().remove(node);
                        break;
                    }
                }
                //always show the first board from the updated workspace
                if (workspace.getChildren().isEmpty())
                    addNewBoard("New Board");
                WorkspaceBoard firstWBoard = (WorkspaceBoard) workspace.getChildren().get(0);
                showBoard(firstWBoard);
                boardName.clear();
            }
            //refresh workspace
            initializeWorkspace();
        }
    }

    /**
     * Deletes a board from the database as well as from the workspace.
     */
    public void deleteBoard() {
        Optional<ButtonType> result = this.showError("Delete Board", true);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            WorkspaceBoard boardToShow = null;
            for (Node node : workspace.getChildren()) {
                WorkspaceBoard wboard = (WorkspaceBoard) node;
                if (wboard.getBoardId() != this.getId()) {
                    boardToShow = wboard;
                    break;
                }
            }
            for (Cardlist cardlist : server.getCardLists(this.id)) {
                for (Card card : server.getCards(cardlist.getId())) {
                    server.deleteCard(card.getId());
                }
                server.deleteCardList(cardlist.getId());
            }
            server.deleteBoard(this.id);
            if (boardToShow == null) {
                boardToShow = addNewBoard("New Board");
            }
            showBoard(boardToShow);
            boardName.clear();
            initializeWorkspace();
        }
    }

    /**
     * Method that shows an alert before leaving/deleting a board
     * @param message The title of the alert
     * @param action true in case of deletion, false in case of leaving action
     * @return The user's response to the alert in the form of a button.
     */
    public Optional<ButtonType> showError(String message, Boolean action) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String act = action ? "delete " : "leave ";
        alert.setHeaderText("You are about to " + act + "this board" + "!");
        alert.setTitle(message);
        alert.setContentText("Are you sure?");
        var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));

        return alert.showAndWait();
    }


    /**
     * Method that is always called at the beginning of showing the BoardView scene that sets up the scene.
     */
    public void myinitialize() {

        boardTitle.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editBoardTitle(); }); // double click to edit.
        refreshButton.setOnAction(e -> refreshBoard());
        // place the side menu off scene
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sideMenu);
        translate.setToX(-300);
        translate.play();
        refreshBoard();
        server.registerForCards("/topic/cards", card -> refreshBoard());

        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);

        // Event for the image which acts like a button to open the side menu
        menuHamburgerClosed.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            showMenu();
            event.consume();
        });

        // Event for the image which acts like a button to close the side menu
        menuHamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            hideMenu();
            event.consume();
        });

        // Event for the close button image so that it acts as a button that switches to the welcome screen
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainCtrl.showOverview();
            hideMenu();
            event.consume();
        });

        //Display first board when opening the app
        if(server.getBoardList().isEmpty()) {
            //Case where there are no boards created.
            //Creates a new board.
            boardTitle.setText("Board Name");
            Board saved = services.createNewBoard(boardTitle.getText());
            this.id = saved.getId();
        } else {
            //Case where boards already exist.
            //Gets first board in the workspace.
            var b = services.getFirstBoardInWorkspaceService();
            this.id = b.getId();
            boardTitle.setText(b.boardName);
        }

        //When you update the board, run the refresh board method.
        server.registerForUpdates(board -> Platform.runLater(this::refreshBoard));

        initializeWorkspace();
        refreshBoard();
    }

    public void editBoardTitle(){

        // Saving label's text
        String labelBackup = boardTitle.getText();

        // Preparing the TextField
        TextField textField = new TextField();
        textField.setFont(Font.font("System",17));
        textField.setLayoutX(boardTitle.getLayoutX());
        textField.setLayoutY(boardTitle.getLayoutY());
        textField.setAlignment(Pos.CENTER);

        textField.setText(labelBackup);
        boardTitle.setText("");
        boardTitle.setGraphic(textField);

        // Keyboard focused on textField
        textField.requestFocus();

        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                confirmChange(textField);
            }
        });

        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                confirmChange(textField);
            }
            else if(e.getCode().equals(KeyCode.ESCAPE)){
                changeTitle(labelBackup);
            }
        });
    }

    public void confirmChange(TextField tf) {
        services.editBoardNameById(this.id, tf.getText());
        changeTitle(tf.getText());
        initializeWorkspace();
    }
    public void changeTitle(String newTitle) {
        boardTitle.setText(newTitle);
        boardTitle.setGraphic(null);
    }

    /**
     * Trigger the stop request on the server.
     */
    public void stop() {
        server.stop();
    }

    public void addTag(){

    }

}
