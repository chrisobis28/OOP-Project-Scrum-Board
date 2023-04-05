package client.components;

import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class TaskView extends AnchorPane {

    @FXML
    private Label label;
    @FXML
    private CheckBox checkBox;
    private commons.Task task;
    private Card card;

    public TaskView(Task task){
        this.task = task;
        this.card = card;
    }

    public void loadTaskView(){
        if(task!=null){
            this.label.setText(task.getDescription());
            this.checkBox.setSelected(task.isCompleted_status());

//            this.label.setOnMouseClicked(event -> {
//                if(event.getClickCount() == 2){
//                    modifyTaskTitle();
//                }
//            });
        }
    }

//    public void modifyTaskTitle(){
//        String backup = label.getText();
//
//        // Set up the TextField.
//        TextField textField = new TextField(backup);
//        textField.setFont(Font.font("System",17));
//        textField.setLayoutX(label.getLayoutX());
//        textField.setLayoutY(label.getLayoutY());
//        textField.setTranslateY(label.getTranslateY()-4);
//        textField.setAlignment(Pos.CENTER);
//
//        // hide the label and cover it with the TextField.
//        label.setText("");
//        label.setGraphic(textField);
//
//        // Make the TextField be the focus for keyboard inputs.
//        textField.requestFocus();
//
//        // End the editing process if focus is changed.
//        textField.focusedProperty().addListener((prop, o , n) -> {
//            if(!n){
//                label.setText(textField.getText());
//                label.setGraphic(null);
//                task.setDescription(label.getText());
//                card.sendEdit();
//            }
//        });
//
//        // On pressing ENTER -> submit changes
//        //             ESCAPE -> cancel changes.
//        textField.setOnKeyReleased(e -> {
//            if(e.getCode().equals(KeyCode.ENTER)){
//                label.setText(textField.getText());
//                label.setGraphic(null);
//                task.setDescription(label.getText());
//                card.sendEdit();
//            }else if(e.getCode().equals(KeyCode.ESCAPE)){
//                label.setText(textField.getText());
//                label.setGraphic(null);
//                task.setDescription(label.getText());
//                card.sendEdit();
//            }
//        });
//    }
}
