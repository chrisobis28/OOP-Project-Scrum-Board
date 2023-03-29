package client.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class Task extends Node {


  @FXML
  private Label task;
  @FXML
  private CheckBox checkBox;
  @FXML
  private Button deleteTask;

  public Task() {
  this.deleteTask.setOnAction(event -> deleteTask());
  }
//TODO it will delete the task from the list of tasks
  private void deleteTask() {
  }


  //TODO it will set the string value of the task
  public void setTask(String desctripion) {

    //this.task.setText(desctripion);
  }



}



