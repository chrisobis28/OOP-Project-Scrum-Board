package client.components;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class Card extends Node {

    @FXML
    private String title = "NEW CARD";


    private ServerUtils server;
    private client.scenes.MainCtrl mainCtrl;
    private commons.Card card;

    @FXML
    public Button cardDeleteButton;

    public Card(){

    }

    public void setCard(String text1, String text2){
        card = new commons.Card(text1, text2);
    }

    public commons.Card getCard() {
        return card;
    }

    public void initDrag(){

        setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(card.id));
            db.setContent(content);
            event.consume();
        });
    }

    public Button getCardDeleteButton() {
        return cardDeleteButton;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
