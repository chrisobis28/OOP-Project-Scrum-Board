package client.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Set;


public class Cardlist extends AnchorPane {
    @FXML
    private Label listname;
    @FXML
    private VBox tasks;

    private Set<Card> cardSet;

    @FXML
    private Button mybutton;

    public Cardlist() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Cardlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(Cardlist.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mybutton.setOnAction(event -> addCard());
        this.mybutton.setText("Add a Card");
    }

    public void addCard(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Card.fxml"));
        try {
            Node node = fxmlLoader.load();
            tasks.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // GETTERS AND SETTERS
    public Label getListname() {
        return listname;
    }

    public void setListname(String listname) {
        this.listname.setText(listname);
    }

    public VBox getTasks() {
        return tasks;
    }
}
