package client.scenes;

import client.components.CardList;
import client.components.WorkspaceBoard;
import client.utils.ServerUtils;
import commons.Board;
import commons.Cardlist;
import javafx.animation.TranslateTransition;
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
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoardViewCtrl implements Initializable {

    private long id;
    public ObservableList<Node> data;

    private final ServerUtils server;
    @FXML
    AnchorPane sideMenu, sideMenuClosed;

    @FXML
    ImageView menuHamburger, menuHamburgerClosed, closeButton;

    @FXML
    private FlowPane board, workspace;

    @FXML
    private Button newListButton;

    @FXML
    private Label boardTitle;
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TextField boardName;

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
     * Creates a common type card list to send to the repository to update.
     * @param cardlist the card list from the client.
     */
    public void sendEdit(CardList cardlist) {
        Cardlist edited = new Cardlist(cardlist.getCardlistId(), cardlist.getListname().getText());
        server.editCardList(edited);
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
            server.addBoard(newBoard);

            var b = new WorkspaceBoard(this);
            b.setBoardName(name);
            b.setId(newBoard.getId());
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

    public void showBoard(WorkspaceBoard boardToShow) {
        this.id = boardToShow.getBoardId();
        this.boardTitle.setText(boardToShow.getBoardName().getText());
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
        for (Board board : server.getBoardList()) {
            if (board.getIsInWorkspace()) {
                var b = new WorkspaceBoard(this);
                b.setBoardName(board.getBoardName());
                b.setId(board.getId());
                workspace.getChildren().add(b);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        boardTitle.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editBoardTitle(); }); // double click to edit.

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
            event.consume();
        });

        if(server.getBoardList().isEmpty()) {
            boardTitle.setText("Board Name");
            Board board = new Board(boardTitle.getText());
            this.id = board.getId();
            board.changeWorkspaceState();
            server.addBoard(board);
        } else {
            for(Board b : server.getBoardList()) {
                if(b.isInWorkspace) {
                    boardTitle.setText(b.boardName);
                    this.id = b.getId();
                    break;
                }
            }
        }


        initializeWorkspace();
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

//    public void sendBoardToServer(String text){
//        Board board = new Board(id, text);
//        server.editBoard(board);
//    }
}