package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;



/**
 * Controller for adding tasks.
 */
public class AddTaskCtrl {
  private final MainCtrl mainCtrl;

  @FXML
  private TextField description;

  @FXML
  private TextField status;


  /**
   * Constructor AddTaskCtrl
   *
   * @param mainCtrl main controller
   */
  @Inject
  public AddTaskCtrl(MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;

  }

}
