package client.scenes;

import client.services.ClientServices;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddCardlistCtrl {

    private ClientServices services;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Stage stage;
    @FXML
    private Button cancel, ok;
    @FXML
    private TextField listName;

    private BoardViewCtrl boardViewCtrl;


    @Inject
    public AddCardlistCtrl(ServerUtils server, MainCtrl mainCtrl, BoardViewCtrl boardViewCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.boardViewCtrl = boardViewCtrl;
        this.services = new ClientServices(server);
    }

    /**
     * Set the board to which this list is added.
     *
     * @param boardViewCtrl the board to which the list is added.
     */
    public void setBoard(BoardViewCtrl boardViewCtrl) {
        this.boardViewCtrl = boardViewCtrl;
    }

    /**
     * stop everything
     */
    public void cancel() {
        stage = (Stage) cancel.getScene().getWindow();
        listName.clear();
        stage.close();
    }

    /**
     * Send the list to the repo.
     */
    public void ok() {
        stage = (Stage) cancel.getScene().getWindow();
        String name;
        if(listName.getText().isEmpty()) name = "New List";
        else name = listName.getText();

        try {
            services.addListToBoardService(name, boardViewCtrl.getId());
        } catch (WebApplicationException e) {
            services.showNewAlert(e);
            return;
        }
        stage.close();
    }

    /**
     * Add functionality for kew presses.
     * @param e a kew press
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

}
