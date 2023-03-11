package client.scenes;


import com.google.inject.Inject;
import commons.Task;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;


public class TaskViewCtrl implements Initializable {
  private final MainCtrl mainCtrl;

  @FXML
  private TableColumn<Task, String> colDescription;
  @FXML
  private TableColumn<Task, String> status;

  /**
   *Constructor for TaskViewCtrl
   *
   */
  @Inject
  public TaskViewCtrl(MainCtrl mainCtrl) {
    this.mainCtrl = mainCtrl;
  }

  /**
   *
   * @param location
   * The location used to resolve relative paths for the root object, or
   * {@code null} if the location is not known.
   *
   * @param resources
   * The resources used to localize the root object, or {@code null} if
   * the root object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    colDescription.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().description));
    status.setCellValueFactory(q -> new SimpleStringProperty("-"));
  }

}
