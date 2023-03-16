package client.scenes;

import client.utils.ServerUtils;
import java.util.Objects;
<<<<<<< HEAD
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
=======
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.*;
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.inject.Inject;


/**
 * Controller for the welcome screen.
 */
public class WelcomeScreenCtrl {

  private final MainCtrl mainCtrl;

  private ServerUtils server;

  @FXML
  private VBox mainVbox;

  @FXML
  private HBox mainHbox;

  @FXML
  private Button connectButton;

  @FXML
  private TextField serverInput;

  @FXML
  private Label title;

  @FXML
  private Label addresstext;


  /**
   * Constructor for WelcomeScreenCtrl.
   *
   * @param mainCtrl main controller
   */
  @Inject
  public WelcomeScreenCtrl(MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;
  }

  /**
   * Method to connect (mostly for button).
   */
  public void connect() {
    System.out.println("You tried to connect to: " + serverInput.getText());
    this.server = new ServerUtils(serverInput.getText());
    if (this.server.checkServerValidity()) {
<<<<<<< HEAD
      mainCtrl.showBoard();
    } else {
      //To add UI in case of invalid server address
      return;
=======
      //switch to board scene.
      mainCtrl.showBoard();
    } else {
      //Create an Alert pop-up and reset the input.
      showError();
      serverInput.setText("");
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
    }
  }

  /**
<<<<<<< HEAD
=======
   * Alert in the case of an invalid server address.
   */
  public void showError() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Address!");
    alert.setContentText(serverInput.getText() + " is not a valid address!");

    //Depending on what the user clicks inside the
    //  alert we can use this to do different things
    Optional<ButtonType> result = alert.showAndWait();
  }
  /**
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
   * Connects to a server when ENTER is pressed.
   *
   * @param e the key pressed
   */
  public void keyPressed(KeyEvent e) {
    if (Objects.requireNonNull(e.getCode()) == KeyCode.ENTER) {
      connect();
    }
  }
}
