package client.components;

import client.scenes.BoardViewCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Set;


public class Cardlist extends AnchorPane {
    private long id;
    private final BoardViewCtrl boardViewCtrl;
    @FXML
    private Label listname;
    @FXML
    private VBox cards;
    @FXML
    private Button toAddCard, toDelete, toEdit;

    /**
     * Card list constructor.
     *
     * @param boardViewCtrl the controller of the board on which this list resides.
     */
    @Inject
    public Cardlist(BoardViewCtrl boardViewCtrl) {
        this.boardViewCtrl = boardViewCtrl;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Cardlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(Cardlist.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        listname.setOnMouseClicked(e -> { if(e.getClickCount() == 2) editTitle(); });
        this.toAddCard.setOnAction(event -> addCard());
        this.toDelete.setOnAction(event -> deleteList());
        this.toAddCard.setText("Add a Card");
        this.toEdit.setOnAction(event -> editTitle());
        Image editIcon = new Image("edit.png");
        this.toEdit.setBackground(new Background(new BackgroundImage(editIcon,
                                                    BackgroundRepeat.NO_REPEAT,
                                                    BackgroundRepeat.NO_REPEAT,
                                                    BackgroundPosition.CENTER,
                                                    BackgroundSize.DEFAULT)));

    }

    /**
     * Add a card to this list.
     */
    public void addCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        try {
            Node node = fxmlLoader.load();
            cards.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void editTitle() {
        String backup = new String(listname.getText());
        TextField textField = new TextField(backup);
        listname.setGraphic(textField);
        textField.requestFocus();
        textField.focusedProperty().addListener((prop, o , n) -> {
            if(!n){
                listname.setGraphic(null);
                listname.setText(textField.getText());
            }
        });
        textField.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)){
                listname.setGraphic(null);
                listname.setText(textField.getText());
            }else if(e.getCode().equals(KeyCode.ESCAPE)){
                textField.setText(backup);
                listname.setGraphic(null);
                listname.setText(textField.getText());
            }
        });
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
