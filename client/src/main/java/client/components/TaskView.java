package client.components;

import client.scenes.CardDetailedViewCtrl;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class TaskView extends AnchorPane {

    @FXML
    private Label label;
    @FXML
    private CheckBox checkBox;
    private commons.Task task;

    private String background;

    @FXML
    private ImageView deleteTask;
    @FXML
    private ImageView moveUp;
    @FXML
    private ImageView moveDown;
    private CardDetailedViewCtrl cardDetailedViewCtrl;

    public TaskView(Task task, CardDetailedViewCtrl cardDetailedViewCtrl){
        this.task = task;
        this.cardDetailedViewCtrl = cardDetailedViewCtrl;
    }

    public void loadTaskView(){
        if(task!=null){
            this.label.setText(task.getDescription());
            this.checkBox.setSelected(task.isCompleted_status());

            this.label.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    modifyTaskTitle();
                }
            });

            checkBox.setSelected(task.isCompleted_status());

            this.checkBox.setOnMouseClicked(event -> {
                task.complete();
                cardDetailedViewCtrl.getComponentCard().sendEdit();
            });

            this.deleteTask.setOnMouseClicked(event -> {
                cardDetailedViewCtrl.deleteTask(task);
            });

            this.moveUp.setOnMouseClicked(event -> {
                cardDetailedViewCtrl.moveTaskUp(task);
                cardDetailedViewCtrl.getComponentCard().sendEdit();
            });

            this.moveDown.setOnMouseClicked(event -> {
                cardDetailedViewCtrl.moveTaskDown(task);
                cardDetailedViewCtrl.getComponentCard().sendEdit();
            });
        }
    }

    public void modifyTaskTitle(){
        String backup = label.getText();

        // Set up the TextField.
        TextField textField = new TextField(backup);
        textField.setLayoutX(label.getLayoutX());
        textField.setLayoutY(label.getLayoutY());
        textField.setTranslateY(label.getTranslateY()-4);

        // hide the label and cover it with the TextField.
        label.setText("");
        label.setGraphic(textField);

        // Make the TextField be the focus for keyboard inputs.
        textField.requestFocus();

        // End the editing process if focus is changed.
        textField.focusedProperty().addListener((prop, o , n) -> {
            if(!n){
                label.setText(textField.getText());
                label.setGraphic(null);
                task.setDescription(label.getText());
                cardDetailedViewCtrl.getComponentCard().sendEdit();
            }
        });

        // On pressing ENTER -> submit changes
        //             ESCAPE -> cancel changes.
        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                label.setText(textField.getText());
                label.setGraphic(null);
                task.setDescription(label.getText());
                cardDetailedViewCtrl.getComponentCard().sendEdit();
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                label.setGraphic(null);
            }
        });
    }
}
