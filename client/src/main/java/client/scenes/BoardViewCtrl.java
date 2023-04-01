package client.scenes;

import client.components.CardList;
import client.components.WorkspaceBoard;
import client.utils.ServerUtils;
import commons.Board;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;


public class BoardViewCtrl implements Initializable {

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
    private Button newListButton, adminLogin;
    @FXML
    private Button refreshButton, leaveBoardButton, deleteBoardButton;
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

    /**
     * Constructor for the BoardViewCtrl
     *
     * @param mainCtrl the main controller of the application.
     */
    @Inject
    public BoardViewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        adminLoggedIn = false;
    }

    /**
     * Searches the database for a board with the name in the searchbar
     */
    public void search(){
        //TODO: search function
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
    public void addNewBoard(String name) {
        long boardID = boardInRepo(name);
        if (boardID == -1) {
            Board newBoard = new Board(name);
            newBoard.changeWorkspaceState();
            Board saved = server.addBoard(newBoard);
            var b = new WorkspaceBoard(this);
            b.setBoardName(name);
            b.setId(saved.getId());
            workspace.getChildren().add(b);
        }
        else {
            if (!server.getBoardById(boardID).getIsInWorkspace()) {
                var b = new WorkspaceBoard(this);
                b.setBoardName(name);
                b.setId(boardID);
                workspace.getChildren().add(b);
                server.getBoardById(boardID).changeWorkspaceState();
            }
        }
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
     * Function that checks whether a board with a given name is in the database.
     *
     * @param name The name of the board to be checked
     * @return the board's id if the board is present in the repo, -1 otherwise
     */
    public long boardInRepo(String name) {
        if (server.getBoardList().isEmpty())
            return -1;
        for (Board board : server.getBoardList()) {
            if (board.getBoardName().equals(name))
                return board.getId();
        }
        return -1;
    }

    /**
     * Reset all the lists.
     */
    public void refreshBoard() {
        var cardlists = server.getCardLists(this.getId());
        List<Node> nodes = new ArrayList<>();
        for (var cardlist : cardlists) {
            var v = new CardList(this, server, cardlist);
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
                var b = new WorkspaceBoard(this);
                b.setBoardName(board.getBoardName());
                b.setId(board.getId());
                workspace.getChildren().add(b);
            }
        }
        //add boards not in the workspace if the user is logged in as an admin
        if (this.adminLoggedIn) {
            for (Board board : server.getBoardList()) {
                if (!board.getIsInWorkspace()) {
                    var b = new WorkspaceBoard(this);
                    b.setBoardName(board.getBoardName());
                    b.setId(board.getId());
                    workspace.getChildren().add(b);
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
        //change the isInWorkspace field to false so that the board is included in the workspace after refreshing it
        Board board = server.getBoardById(this.getId());
        if (board.getIsInWorkspace()) {
            board.changeWorkspaceState();
            server.editBoard(board);
            //deletes the board with this name from the workspace, not showing it anymore,
            //so we can find a new board to show
            for (Node node : workspace.getChildren()) {
                WorkspaceBoard wboard = (WorkspaceBoard) node;
                if (wboard.getBoardId()==this.getId()) {
                    workspace.getChildren().remove(node);
                    break;
                }
            }
            //always show the first board from the updated workspace
            WorkspaceBoard firstWBoard = (WorkspaceBoard) workspace.getChildren().get(0);
            showBoard(firstWBoard);
        }
        //refresh workspace
        initializeWorkspace();
    }

    /**
     * Deletes a board from the database as well as from the workspace.
     */
    public void deleteBoard() {
        WorkspaceBoard boardToShow = null;
        for (Node node : workspace.getChildren()) {
            WorkspaceBoard wboard = (WorkspaceBoard) node;
            if (wboard.getBoardId() != this.getId()) {
                boardToShow = wboard;
                break;
            }
        }
        server.deleteBoard(this.id);
        showBoard(boardToShow);
        initializeWorkspace();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boardTitle.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editBoardTitle(); }); // double click to edit.
        refreshButton.setOnAction(e -> refreshBoard());
        // place the side menu off scene
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sideMenu);
        translate.setToX(-300);
        translate.play();
        refreshBoard();

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
            Board board = new Board(boardTitle.getText());
            board.changeWorkspaceState();
            Board saved = server.addBoard(board);
            this.id = saved.getId();
        } else {
            //Case where boards already exist.
            //Gets first board in the workspace.
            for(Board b : server.getBoardList()) {
                if(b.getIsInWorkspace()) {
                    boardTitle.setText(b.boardName);
                    this.id = b.getId();
                    break;
                }
            }
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
                boardTitle.setText(textField.getText());
                boardTitle.setGraphic(null);
//                sendBoardToServer(boardTitle.getText());
            }
        });

        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                boardTitle.setText(textField.getText());
                boardTitle.setGraphic(null);
//                sendBoardToServer(boardTitle.getText());
            }
            else if(e.getCode().equals(KeyCode.ESCAPE)){
                boardTitle.setText(labelBackup);
                boardTitle.setGraphic(null);
            }
        });
    }

    /**
     * Trigger the stop request on the server.
     */
    public void stop() {
        server.stop();
    }

//    public void sendBoardToServer(String text){
//        Board board = new Board(id, text);
//        server.editBoard(board);
//    }
}