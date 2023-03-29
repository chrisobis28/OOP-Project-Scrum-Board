package client.components;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Card extends Pane {

    @FXML
    private String title = "NEW CARD";

    @FXML
    private Button cardDeleteButton;

    private ServerUtils server;
    private commons.Card card;

    private CardList cardList;

    public Card(ServerUtils server, commons.Card card, CardList cardList){
        this.server = server;
        this.card = card;
        this.cardList = cardList;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        cardDeleteButton.setOnAction(event -> deleteCard());
        initDrag();
    }
    public void deleteCard(){
        cardList.getCardList().removeCard(card);
        cardList.getCards().getChildren().remove(this);
        server.deleteCard(card.getId());
    }

    public void initDrag(){

        setOnDragDetected(event -> {
            Dragboard db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(card.id));
            db.setContent(content);
            event.consume();
        });

        setOnDragDone(event -> {
            if(event.getTransferMode() == TransferMode.MOVE){
                cardList.getCardList().removeCard(card);
                cardList.getCards().getChildren().remove(this);
            }
            event.consume();
        });
    }

    public CardList getCardList() {
        return cardList;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
