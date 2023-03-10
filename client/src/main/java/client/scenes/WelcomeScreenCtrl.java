package client.scenes;

import client.utils.ServerUtils;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
      mainCtrl.showBoard();
    } else {
      //To add UI in case of invalid server address
      return;
    }
  }

  /**
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
