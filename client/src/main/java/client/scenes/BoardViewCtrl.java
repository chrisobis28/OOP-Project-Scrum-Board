package client.scenes;

import client.components.Cardlist;
import client.components.WorkspaceBoard;
import client.utils.ServerUtils;
import commons.Board;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;
import org.checkerframework.checker.units.qual.A;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoardViewCtrl implements Initializable {
    public ObservableList<Node> data;

    private ArrayList<String> boardsInWorkspace;
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
        this.boardsInWorkspace = new ArrayList<>();
//        for(int i = 0; i < 5; i++){
//            String s = "Board "+i;
//            boards.add(s);
//        }
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
        mainCtrl.showAddList();
    }

    /**
     * Delete a card list by its id.
     *
     * @param id the id of the card list to be deleted.
     */
    public void deleteList(long id) {
        server.deleteCardList(id);

        refreshBoard();
    }

    /**
     * Add a board with a given name to the repo and to the workspace.
     * @param name The name of the new Board
     */
    public void addNewBoard(String name) {
        long boardID = boardInRepo(name);
        if (boardID == -1) {
            Board newBoard = new Board(name);
            server.addBoard(newBoard);

            var b = new WorkspaceBoard(this);
            b.setBoardName(name);
            b.setId(newBoard.getId());
            workspace.getChildren().add(b);
            boardsInWorkspace.add(name);
        }
        else {
            if (!boardsInWorkspace.contains(name)) {
                var b = new WorkspaceBoard(this);
                b.setBoardName(name);
                b.setId(boardID);
                workspace.getChildren().add(b);
                boardsInWorkspace.add(name);
            }
        }
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
        var cardlists = server.getCardList();
        List<Node> nodes = new ArrayList<>();
        for (var cardlist : cardlists) {
            var v = new Cardlist(this);
            v.setListname(cardlist.getCardlistName());
            v.setId(cardlist.getId());
            nodes.add(v);
        }

        data = FXCollections.observableList(nodes);
        board.getChildren().clear();
        board.getChildren().addAll(data);
        data.clear();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
    }
}