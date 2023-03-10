package client.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.inject.Inject;
import javafx.scene.control.TextField;

import java.util.Objects;


/**
 * Controller for the welcome screen.
 */
public class WelcomeScreenCtrl{

    private final MainCtrl mainCtrl;

    @FXML private VBox mainVbox;

    @FXML private HBox mainHbox;

    @FXML private Button connectButton;

    @FXML private TextField serverInput;

    @FXML private Label title;

    @FXML private Label addresstext;


    /**
     * Constructor for WelcomeScreenCtrl
     *
     * @param mainCtrl main controller
     */
    @Inject
    public WelcomeScreenCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Method to connect (mostly for button)
     */
    public void connect() {
        System.out.println("You tried to connect to: " + serverInput.getText());
        if(!serverInput.getText().contains("wrong")) {
            serverInput.setText("");
            mainCtrl.showBoard();
        }
        //TO DO: actually connect to a server with that input lol
    }

    /**
     * Connects to a server when ENTER is pressed
     * @param e the key pressed
     */
    public void keyPressed(KeyEvent e) {
        if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) {
            connect();
        }
    }
}
