package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;

/**
 * Controller for the admin login scene
 */
public class AdminLoginCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private BoardViewCtrl boardViewCtrl;
  private Stage stage;
  @FXML
  private TextField password;
  @FXML
  private Text errorText;

  /**
   * Constructor for the controller
   * @param server the server that the scene is on
   * @param mainCtrl main controller of this scene
   * @param boardViewCtrl board view controller of the scene
   */
  @Inject
  public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl, BoardViewCtrl boardViewCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.boardViewCtrl = boardViewCtrl;
  }

  /**
   * Setter for the boardViewCtrl field.
   * @param boardViewCtrl a BoardViewCtrl instance
   */
  public void setBoardCtrl(BoardViewCtrl boardViewCtrl) {
    this.boardViewCtrl = boardViewCtrl;
  }

  public BoardViewCtrl getBoardCtrl() {return this.boardViewCtrl;}

  public Stage getStage() {return this.stage;}

  /**
   * Setter for the stage field.
   * @param stage the new stage of the controller
   */
  public void setStage(Stage stage) {
    this.stage = stage;
  }

  /**
   * At the beginning of the scene always sets up the error text to being invisible.
   */
  public void setErrorText() {
    errorText.setStyle("-fx-opacity: 0.0");
  }

  /**
   * Checks if the password is correct and either shows the new admin overview if
   * the password is correct, or lets the user try another password, showing an error text
   * if the password is incorrect.
   */
  public void validatePassword() {
    String tried = password.getText();
    if (tried.equals(this.server.getAdminPassword())) {
      errorText.setStyle("-fx-opacity: 0.0");
      password.clear();
      stage.close();
      //show new overview of the admin
      boardViewCtrl.showOverview();
    }
    else {
      //make the error text visible and empty the password TextField
      errorText.setStyle("-fx-opacity: 1.0");
      password.clear();
    }

  }

  public void exitStage() {
    password.clear();
    stage.close();
  }

  public void keyPressed(KeyEvent e) {
    switch (e.getCode()) {
      case ENTER:
        validatePassword();
        break;
      case ESCAPE:
        exitStage();
        break;
      default:
        break;
    }
  }
}
