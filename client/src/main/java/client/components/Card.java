package client.components;

import client.scenes.BoardViewCtrl;
import client.scenes.CardDetailedViewCtrl;
import client.services.ComponentsServices;
import client.utils.ServerUtils;
import commons.Cardlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Card extends Pane {

    @FXML
    private Label title;

    @FXML
    private Button cardDeleteButton;

    private ServerUtils server;
    private commons.Card card;

    private CardList cardList;
    private BoardViewCtrl boardViewCtrl;
    private ComponentsServices componentsServices;

    @FXML
    private ImageView edit;

    /**
     * Constructor to help with testing by removing the fxml part.
     * @param boardViewCtrl controller of the card
     * @param server server of the card
     * @param card the associated commons card
     * @param cardList the card's cardlist
     * @param componentsServices the service class instance for this card
     */
    public Card(BoardViewCtrl boardViewCtrl, ServerUtils server, commons.Card card, CardList cardList, ComponentsServices componentsServices) {
        this.server = server;
        this.card = card;
        this.cardList = cardList;
        this.boardViewCtrl = boardViewCtrl;
        this.componentsServices = componentsServices;
    }

    public Card(BoardViewCtrl boardViewCtrl, ServerUtils server, commons.Card card, CardList cardList){
        this.server = server;
        this.card = card;
        this.cardList = cardList;
        this.boardViewCtrl = boardViewCtrl;
        this.componentsServices = new ComponentsServices(server);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        this.title.setText(card.getCardName());
        title.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editTitle(); }); // double click to edit.
        cardDeleteButton.setOnAction(event -> deleteCard());
        edit.setOnMouseClicked(event -> editCard());
        initDrag();
    }
    public void deleteCard(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to delete \"" + this.title.getText() + "\"!");
        alert.setTitle("Delete card");
        alert.setContentText("Are you sure?");
        var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            removeThisCard();
            //cardList.getCardList().removeCard(card);
            server.deleteCard(card.getId());
            server.editCardList(cardList.getCardList());
            server.editBoard(server.getBoardById(boardViewCtrl.getId()));
        }
    }

    public void removeThisCard() {
        for (commons.Card card1 : server.getCards(cardList.getCardList().getId())) {
            if (card1.getPosition()>card.getPosition()) {
                card1.setPosition(card1.getPosition() - 1);
                server.editCard(card1);
            }
        }
    }

    public void editCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/CardDetailedView.fxml"));
        CardDetailedViewCtrl cardDetailedViewCtrl = new CardDetailedViewCtrl(boardViewCtrl, server);
        fxmlLoader.setController(cardDetailedViewCtrl);
        fxmlLoader.setRoot(cardDetailedViewCtrl);
        try{
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getIcons().add(new Image("icon.png"));
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        cardDetailedViewCtrl.load(card,this);
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
//                for (commons.Card card1 : server.getCards(cardList.getCardList().getId())) {
//                    if (card1.getId()==card.getId()) {
//                        cardList.getCardList().removeCard(card1);
//                        break;
//                    }
//                }
//                for (Cardlist cardlist : server.getBoardById(boardViewCtrl.getId()).getCardlistList()) {
//                    if (cardlist.getId()==cardList.getCardList().getId()) {
//                        cardlist.removeCard(card);
//                        break;
//                    }
//                }
                long i = 0;
                for (commons.Card card1 : server.getCards(cardList.getCardList().getId())) {
                    card1.setPosition(i);
                    i++;
                    server.editCard(card1);
                }
                server.editCardList(cardList.getCardList());
                cardList.getCards().getChildren().remove(this);
                server.editBoard(server.getBoardById(boardViewCtrl.getId()));
            }
            event.consume();
        });
    }

    public CardList getCardList() {
        return cardList;
    }

//    public String getTitle(){
//        return this.title.getText();
//    }

    /**
     * Allow the user to edit a list title by showing a TextField over the label
     *  then taking the updated text and replacing it in the label.
     */
    public void editTitle() {
        String backup = title.getText(); // the initial title.

        // Set up the TextField.
        TextField textField = new TextField(backup);
        textField.setFont(Font.font("System",12));
        textField.setLayoutX(title.getLayoutX());
        textField.setLayoutY(title.getLayoutY());
        textField.setTranslateY(title.getTranslateY()-4);
        textField.setAlignment(Pos.CENTER);

        // hide the label and cover it with the TextField.
        title.setText("");
        title.setGraphic(textField);

        // Make the TextField be the focus for keyboard inputs.
        textField.requestFocus();

        // End the editing process if focus is changed.
        textField.focusedProperty().addListener((prop, o , n) -> {
            if(!n){
                toLabel(textField);
                card.setCardName(textField.getText());
                sendEdit();
            }
        });

        // On pressing ENTER -> submit changes
        //             ESCAPE -> cancel changes.
        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                toLabel(textField);
                card.setCardName(textField.getText());
                sendEdit();
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                textField.setText(backup);
                toLabel(textField);
                card.setCardName(textField.getText());
                sendEdit();
            }
        });
    }

    /**
     * Restore the card name label and change the text to the updated text.
     *
     * @param tf The updated text.
     */
    public void toLabel(TextField tf){
        title.setGraphic(null);
        title.setText(tf.getText());
    }

    /**
     * Pass this card to the server to save the update.
     */
    public void sendEdit() {
        for (commons.Card card1 : server.getCards(cardList.getCardList().getId())) {
            if (card1.getId() == card.getId())
                card1.setCardName(card.getCardName());
        }
        server.editCard(card);
        componentsServices.CardlistSendEdit(boardViewCtrl.getId(), cardList.getCardList());
    }

    public commons.Card getCard() {
        return card;
    }

//    public void setTitle(String title){
//        this.title.setText(title);
//    }
}
