package client.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class Card extends Node {

    @FXML
    private String title = "NEW CARD";

    @FXML
    public Button cardDeleteButton;

    public Card(){

    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
