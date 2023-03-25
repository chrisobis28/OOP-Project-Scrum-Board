package client.components;

import client.scenes.BoardViewCtrl;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class Cardlist extends AnchorPane {
    private long id;
    private final BoardViewCtrl boardViewCtrl;
    @FXML
    private Label listname;
    @FXML
    private VBox tasks;
    @FXML
    private Button mybutton;
    @FXML
    private Button todelete;

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

        this.mybutton.setOnAction(event -> addCard());
        this.todelete.setOnAction(event -> deleteList());
        this.mybutton.setText("Add a Card");
    }

    /**
     * Add a card to this list.
     */
    public void addCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        try {
            Node node = fxmlLoader.load();
            tasks.getChildren().add(node);
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
    public VBox getTasks() {
        return tasks;
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
