package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;


public class AdminLoginCtrl {

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private BoardViewCtrl boardViewCtrl;
  private Stage stage;
  @FXML
  private TextField password;
  @FXML
  private Text errorText;


  @Inject
  public AdminLoginCtrl(ServerUtils server, MainCtrl mainCtrl, BoardViewCtrl boardViewCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.boardViewCtrl = boardViewCtrl;
  }

  public void setBoardCtrl(BoardViewCtrl boardViewCtrl) {
    this.boardViewCtrl = boardViewCtrl;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void validatePassword() {
    String tried = password.getText();
    if (tried.equals(this.server.getAdminPassword())) {

      errorText.setStyle("-fx-opacity: 0.0");
      password.clear();
      stage.close();
      //show overview option
    }
    else {
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
