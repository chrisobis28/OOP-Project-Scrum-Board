package client.scenes;

import client.components.Cardlist;
import client.utils.ServerUtils;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoardViewCtrl implements Initializable {
    public ObservableList<Node> data;
    private final ServerUtils server;
    @FXML
    AnchorPane sideMenu, sideMenuClosed;

    @FXML
    ImageView menuHamburger, menuHamburgerClosed, closeButton;

    @FXML
    private FlowPane board;

    @FXML
    private Button newListButton;
    @FXML
    private ScrollPane scrollpane;

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
     * reset all the lists.
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