package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

import javax.inject.Inject;


public class BoardViewCtrl{
    public ObservableList<String> boards;

    @FXML
    private TableView<String> table;

    @FXML
    private TableColumn<String, String> colTable;

    private final MainCtrl mainCtrl;

    /**
     * Constructor for the BoardViewCtrl
     * Right now also creates a list of boards
     * @param mainCtrl
     */
    @Inject
    public BoardViewCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        boards = FXCollections.observableArrayList();
        for(int i = 0; i < 5; i++){
            String s = "Board "+i;
            boards.add(s);
        }
        refresh();
    }

    /**
     * Searches the database for a board with the name in the searchbar
     */
    public void search(){
        refresh();
    }
    /**
     * Refreshes the list of boards
     **/
    public void refresh(){
        //table.setItems(boards);
    }

    /**
     * For handling e key pressed
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        search();
    }
}
