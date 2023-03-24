package client.components;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;

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
