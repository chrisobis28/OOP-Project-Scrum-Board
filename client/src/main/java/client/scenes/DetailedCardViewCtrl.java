package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailedCardViewCtrl {
  private final MainCtrl mainCtrl;
  private final ServerUtils server;

  @FXML
  private VBox taskList;
  @FXML
  private Button addTask;
  @FXML
  private Button closeDetailedCardView;
  @FXML
  private Label descrption, cardName;


  @Inject
  public DetailedCardViewCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;
    this.server = server;
  }


  public void closeView(){
    Stage stage = (Stage) closeDetailedCardView.getScene().getWindow();
    stage.close();
  }

}
