package client.components;

import client.scenes.BoardViewCtrl;
import client.scenes.MainCtrl;
import client.services.ComponentsServices;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Cardlist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class CardList extends AnchorPane {
    private long id;
    private final BoardViewCtrl boardViewCtrl;
    @FXML
    private Label listname;
    @FXML
    private VBox cards;
    @FXML
    private Button toAddCard;
    @FXML
    private Button toDelete;
    @FXML
    private Button toEdit;
    private ServerUtils server;

    private Cardlist cardList;
    private MainCtrl mainCtrl;

    private ComponentsServices componentsServices;

    /**
     * Card list constructor.
     *
     * @param boardViewCtrl the controller of the board on which this list resides.
     */
    @Inject
    public CardList(MainCtrl mainCtrl, BoardViewCtrl boardViewCtrl, ServerUtils server, Cardlist cardList) {
        this.mainCtrl = mainCtrl;
        this.boardViewCtrl = boardViewCtrl;
        this.server = server;
        this.cardList = cardList;
        this.componentsServices = new ComponentsServices(server);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Cardlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardList.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

//        constructVBox();

        this.listname.setText(cardList.getCardlistName());

        listname.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editTitle(); }); // double click to edit.
        this.toAddCard.setOnAction(event -> componentsServices.addCardToCardlist(cardList, boardViewCtrl));
        this.toDelete.setOnAction(event -> deleteList());
        this.toAddCard.setText("Add a Card");
        this.toEdit.setOnAction(event -> editTitle());

        Image editIcon = new Image("edit.png");
        ImageView editIconView = new ImageView(editIcon);
        editIconView.setFitHeight(17);
        editIconView.setPreserveRatio(true);
        toEdit.setGraphic(editIconView);

        initDrop();
    }

    public void initDrop(){
        setOnDragOver(event -> {
            if (event.getGestureSource() instanceof Card) {
                event.acceptTransferModes(TransferMode.MOVE);

//                Card sourceCard = (Card) event.getGestureSource();
//                if (sourceCard.getCardList() != this) {
//                    event.acceptTransferModes(TransferMode.MOVE);
//                }
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if(db.hasString()){
                try{
                    long cardId = Long.parseLong(db.getString());
                    commons.Card commonCard = server.getCardById(cardId);
//                    for (commons.Card card : server.getCards(cardList.getId())) {
//                        if (card.getId()== commonCard.getId()) {
//                            card.setCardList(cardList);
//                            break;
//                        }
//                  }
                    boolean changeoflists= commonCard.getCardlistID() != cardList.getId();
                    commonCard.setCardList(cardList);
                    server.editCard(commonCard);
                    Card card = new Card(boardViewCtrl, server, commonCard, this);

                    int index = cards.getChildren().size();
                    if(index!= 0 && event.getY() < cards.getChildren().get(0).getBoundsInParent().getMinY()){
                        index = 0;
                    }
                    else if(index!= 0 && event.getY() > cards.getChildren().get(index - 1).getBoundsInParent().getMaxY()){
                        index = cards.getChildren().size();
                    }
                    else{
                        for(int i =0; i<cards.getChildren().size(); ++i){
                            if(event.getY() < cards.getChildren().get(i).getBoundsInParent().getMaxY()){
                                index = i;
                                break;
                            }
                        }
                    }
                    commonCard.setPosition((long) index);
                    server.editCard(commonCard);
                    for (commons.Card card1 : server.getCards(cardList.getId())) {
                        if (changeoflists&&card1.getPosition()>=commonCard.getPosition()&&card1.getId()!=commonCard.getId()) {
                            card1.setPosition(card1.getPosition()+1);
                            server.editCard(card1);
                        }
                    }

                    //cardList.addCard(index, commonCard);
                    //System.out.println(commonCard.getPosition());
                    var board = server.getBoardById(boardViewCtrl.getId());
//                    for(commons.Cardlist cardlist : server.getCardLists(board.getId())){
//                        if(cardlist.getId() == cardList.getId()){
//                            cardlist.addCard(index, commonCard);
//                            break;
//                        }
//                    }
                    server.editCardList(cardList);
                    cards.getChildren().add(index, card);
                    success = true;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    /*public void constructVBox(){
        for(commons.Card card : cardList.getCardSet()){
            cards.getChildren().add(new Card(boardViewCtrl, server, card, this));
        }
    }*/

    /**
     * Deletes this list.
     * Shows a Confirmation alert before deleting to avoid
     *  the user accidentally deleting a list.
     */
    public void deleteList() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("You are about to delete \"" + this.listname.getText() + "\"!");
        alert.setTitle("Delete list");
        alert.setContentText("Are you sure?");
        var stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("icon.png"));

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            componentsServices.deleteList(boardViewCtrl.getId(), cardList);
        }
    }

    /**
     * Allow the user to edit a list title by showing a TextField over the label
     *  then taking the updated text and replacing it in the label.
     */
    public void editTitle() {
        String backup = listname.getText(); // the initial title.

        // Set up the TextField.
        TextField textField = new TextField(backup);
        textField.setFont(Font.font("System",17));
        textField.setLayoutX(listname.getLayoutX());
        textField.setLayoutY(listname.getLayoutY());
        textField.setTranslateY(listname.getTranslateY()-4);
        textField.setAlignment(Pos.CENTER);

        // hide the label and cover it with the TextField.
        listname.setText("");
        listname.setGraphic(textField);

        // Make the TextField be the focus for keyboard inputs.
        textField.requestFocus();

        // End the editing process if focus is changed.
        textField.focusedProperty().addListener((prop, o , n) -> {
            if(!n){
                toLabel(textField);
                cardList.setCardlistName(textField.getText());
                componentsServices.CardlistSendEdit(boardViewCtrl.getId(), cardList);
            }
        });

        // On pressing ENTER -> submit changes
        //             ESCAPE -> cancel changes.
        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                toLabel(textField);
                cardList.setCardlistName(textField.getText());
                componentsServices.CardlistSendEdit(boardViewCtrl.getId(), cardList);
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                textField.setText(backup);
                toLabel(textField);
                cardList.setCardlistName(textField.getText());
                componentsServices.CardlistSendEdit(boardViewCtrl.getId(), cardList);
            }
        });
    }

    /**
     * Restore the list name label and change the text to the updated text.
     *
     * @param tf The updated text.
     */
    public void toLabel(TextField tf){
        listname.setGraphic(null);
        listname.setText(tf.getText());
    }

    // GETTERS AND SETTERS

//    /**
//     * Get the list name.
//     *
//     * @return Label with the list name.
//     */
//    public Label getListname() {
//        return listname;
//    }

    public Cardlist getCardList() {
        return cardList;
    }

//    /**
//     * Sets the list name to be a given String.
//     *
//     * @param listname the updated name of the list.
//     */
//    public void setListname(String listname) {
//        this.listname.setText(listname);
//    }

    /**
     * Gets the VBox containing the tasks.
     *
     * @return the VBox containing tasks.
     */
    public VBox getCards() {
        return cards;
    }

    /**
     * Set the id of the list.
     *
     * @param id ID for the list.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the ID of the list.
     *
     * @return ID of the list.
     */
    public long getCardlistId() {
        return this.id;
    }
}
