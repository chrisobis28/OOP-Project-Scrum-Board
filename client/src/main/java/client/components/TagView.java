package client.components;

import commons.Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class TagView extends AnchorPane {

    @FXML
    private Label label;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView deleteImage;
    @FXML
    private ImageView editImage;
    private commons.Tag tag;
    private Card card;

    private String background;

    public TagView(Tag tag, Card card){
        this.tag = tag;
        this.card = card;

        Random random = new Random();

        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        this.background = String.format("#%02x%02x%02x", red, green, blue);
    }

    public void loadTagView(){
        if(tag!=null){
            this.label.setText(tag.getTagName());
            this.pane.setStyle("-fx-background-color: " + tag.getBackgroundColour());
//            this.label.setOnMouseClicked(event -> {
//                if(event.getClickCount() == 2){
//                    modifyTagTitle();
//                }
//            });
        }
    }
}
