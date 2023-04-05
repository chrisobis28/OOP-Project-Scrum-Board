package client.scenes;

import client.components.TaskView;
import client.utils.ServerUtils;
import commons.Card;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class CardDetailedViewCtrl extends AnchorPane {

    @FXML
    private TextField cardTitleField;
    @FXML
    private TextArea cardDescriptionField;
    @FXML
    private VBox tasksVBOX;
    @FXML
    private VBox tagsVBOX;

    private Card card;
    private client.components.Card componentCard;

    @FXML
    private Button addTaskButton;
    @FXML
    private Button addTagButton;
    private BoardViewCtrl boardViewCtrl;
    private ServerUtils server;

    public CardDetailedViewCtrl(BoardViewCtrl boardViewCtrl, ServerUtils server){
        this.boardViewCtrl = boardViewCtrl;
        this.server = server;
    }

    public void load(Card card, client.components.Card compCard){
        this.card = card;
        this.componentCard = compCard;
        cardTitleField.setText(this.card.getCardName());
        cardTitleField.setEditable(false);
        cardDescriptionField.setText(this.card.getCardDescription());
        cardDescriptionField.setEditable(false);
        for(Task task : this.card.getTaskList()){
            addTaskToVBOX(task);
        }
        for(Tag tag : this.card.getTagList()){
            //to be updated
        }
        this.setTextFunctionalities();
        this.setButtons();
    }

    public void setTextFunctionalities(){
        cardTitleField.setOnMouseClicked(event ->{
            if(event.getClickCount() == 2) {
                cardTitleField.setEditable(true);
                cardTitleField.requestFocus();
            }
        });
        cardDescriptionField.setOnMouseClicked(event ->{
            if(event.getClickCount() == 2){
                cardDescriptionField.setEditable(true);
                cardDescriptionField.requestFocus();
            }
        });

        cardTitleField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                card.setCardName(cardTitleField.getText());
                componentCard.sendEdit();
                cardTitleField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
        cardTitleField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                card.setCardName(cardTitleField.getText());
                componentCard.sendEdit();
                cardTitleField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });

        cardDescriptionField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                card.setCardName(cardDescriptionField.getText());
                componentCard.sendEdit();
                cardDescriptionField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
        cardDescriptionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                card.setCardDescription(cardDescriptionField.getText());
                componentCard.sendEdit();
                cardDescriptionField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
    }

    public void setButtons(){
        this.addTaskButton.setOnAction(event -> {
            createTask();
        });
    }

    public void createTask(){
        Task task = new Task("sss", card);
        Task otherTask = server.addTask(task);
        card.getTaskList().add(otherTask);
        componentCard.sendEdit();
        //STOP HERE IN CASE
        addTaskToVBOX(otherTask);
    }

    public void addTaskToVBOX(Task task){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/TaskView.fxml"));
        TaskView taskView = new TaskView(task);
        fxmlLoader.setController(taskView);
        try{
            Parent root = fxmlLoader.load();
            taskView.loadTaskView();
            tasksVBOX.getChildren().add(root);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
