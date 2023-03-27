package client.components;

import client.scenes.BoardViewCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    private Button toAddCard, toDelete, toEdit;
    private ServerUtils server;

    /**
     * Card list constructor.
     *
     * @param boardViewCtrl the controller of the board on which this list resides.
     */
    @Inject
    public CardList(BoardViewCtrl boardViewCtrl, ServerUtils server) {
        this.boardViewCtrl = boardViewCtrl;
        this.server = server;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Cardlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(CardList.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        listname.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editTitle(); }); // double click to edit.
        this.toAddCard.setOnAction(event -> addCard());
        this.toDelete.setOnAction(event -> deleteList());
        this.toAddCard.setText("Add a Card");
        this.toEdit.setOnAction(event -> editTitle());

        Image editIcon = new Image("edit.png");
        ImageView editIconView = new ImageView(editIcon);
        editIconView.setFitHeight(17);
        editIconView.setPreserveRatio(true);
        toEdit.setGraphic(editIconView);
    }

    /**
     * Add a card to this list.
     */
    public void addCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        try {
            Node node = fxmlLoader.load();
            Card card = fxmlLoader.getController();
            card.setCard("Name", "Description");
            server.addCard(card.getCard());
            card.getCardDeleteButton().setOnAction(event -> deleteCard(card));
            cards.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCard(Card card){
        cards.getChildren().remove(card);
        server.deleteCard(card.getCard().getId());
    }

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
            boardViewCtrl.deleteList(id);
        }
    }

    /**
     * Allow the user to edit a list title by showing a TextField over the label
     *  then taking the updated text and replacing it in the label.
     */
    public void editTitle() {
        String backup = new String(listname.getText()); // the initial title.

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
                sendEdit();
            }
        });

        // On pressing ENTER -> submit changes
        //             ESCAPE -> cancel changes.
        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                toLabel(textField);
                sendEdit();
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                textField.setText(backup);
                toLabel(textField);
                sendEdit();
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

    /**
     * Pass this card list to the board view controller to send the update to the server.
     */
    public void sendEdit() {
        boardViewCtrl.sendEdit(this);
    }

    // GETTERS AND SETTERS

    /**
     * Get the list name.
     *
     * @return Label with the list name.
     */
    public Label getListname() {
        return listname;
    }

    /**
     * Sets the list name to be a given String.
     *
     * @param listname the updated name of the list.
     */
    public void setListname(String listname) {
        this.listname.setText(listname);
    }

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
