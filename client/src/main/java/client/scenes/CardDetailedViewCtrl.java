package client.scenes;

import client.utils.ServerUtils;
import commons.Card;
import commons.Tag;
import commons.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


public class CardDetailedViewCtrl extends AnchorPane {

    @FXML
    private TextField cardTitleField;
    @FXML
    private TextArea cardDescriptionField;
    @FXML
    private ScrollPane tasksScrollPane;
    @FXML
    private ScrollPane tagsScrollPane;

    private Card card;

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

    public void load(Card card){
        this.card = card;
        cardTitleField.setText(this.card.getCardName());
        cardTitleField.setEditable(false);
        cardDescriptionField.setText(this.card.getCardDescription());
        cardDescriptionField.setEditable(false);
        for(Task task : this.card.getTaskList()){
            //to be updated
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
                server.editCard(card);
                server.editCardList(card.getCardlist());
                cardTitleField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
        cardTitleField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                card.setCardName(cardTitleField.getText());
                server.editCardList(card.getCardlist());
                cardTitleField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });

        cardDescriptionField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                card.setCardName(cardDescriptionField.getText());
                server.editCardList(card.getCardlist());
                cardDescriptionField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
        cardDescriptionField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                card.setCardDescription(cardDescriptionField.getText());
                server.editCardList(card.getCardlist());
                cardDescriptionField.setEditable(false);
                boardViewCtrl.refreshBoard();
            }
        });
    }

    public void setButtons(){

    }
}
