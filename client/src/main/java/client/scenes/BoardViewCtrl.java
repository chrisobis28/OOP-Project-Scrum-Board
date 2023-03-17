package client.scenes;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.inject.Inject;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoardViewCtrl implements Initializable {
    public ObservableList<String> boards;

    @FXML
    AnchorPane sideMenu, sideMenuClosed;

    @FXML
    ImageView menuHamburger, menuHamburgerClosed, closeButton;

    @FXML
    private TableView<String> table;

    @FXML
    private TableColumn<String, String> colTable;

    private final MainCtrl mainCtrl;

    /**
     * Constructor for the BoardViewCtrl
     * Right now also creates a list of boards
     * @param mainCtrl the main controller of the application.
     */
    @Inject
    public BoardViewCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        boards = FXCollections.observableArrayList();
        for(int i = 0; i < 5; i++){
            String s = "Board "+i;
            boards.add(s);
        }
        refresh();
    }

    /**
     * Searches the database for a board with the name in the searchbar
     */
    public void search(){
        refresh();
    }
    /**
     * Refreshes the list of boards
     **/
    public void refresh(){
        //table.setItems(boards);
    }

    /**
     * For handling e key pressed
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // place the side menu off scene
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(sideMenu);
        translate.setToX(-300);
        translate.play();

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

        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            mainCtrl.showOverview();
            event.consume();
        });
    }
}