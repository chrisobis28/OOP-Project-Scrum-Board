package client.components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class Cardlist extends AnchorPane {
    @FXML
    private Label listname;
    @FXML
    private VBox tasks;

    public Cardlist() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/Cardlist.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(Cardlist.this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

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
