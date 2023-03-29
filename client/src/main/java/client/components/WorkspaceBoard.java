package client.components;

import client.scenes.BoardViewCtrl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.IOException;

public class WorkspaceBoard extends HBox {
  private long id;

  private final BoardViewCtrl boardViewCtrl;

  @FXML
  private Text boardName;

  @FXML
  private Button open;

  /**
   * WorkspaceBoard constructor.
   * @param boardViewCtrl the controller of this board
   */
  @Inject
  public WorkspaceBoard(BoardViewCtrl boardViewCtrl) {
    this.boardViewCtrl = boardViewCtrl;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client.components/WorkspaceBoard.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(WorkspaceBoard.this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    this.setOnMouseEntered(e -> highlight());
    this.setOnMouseExited(e -> dehighlight());
    this.open.setOnAction(event -> openBoard());
  }

  /**
   * Open a board and show it to the user.
   */
  public void openBoard() {
    //TO-DO : function that opens this board on the BoardView scene
  }

  /**
   * Make the board card white.
   */
  public void highlight() {
    this.setBackground(new Background(new BackgroundFill(
            Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
  }

  /**
   * Make the board card the same color as the background.
   */
  public void dehighlight() {
    this.setBackground(new Background(new BackgroundFill(
            Color.rgb(210,210,210), CornerRadii.EMPTY, Insets.EMPTY)));
  }

  //GETTERS AND SETTERS

  /**
   * Get the board's name.
   *
   * @return TextField with the board's name.
   */
  public Text getBoardName() { return boardName; }

  /**
   * Sets the board's name to a given String.
   *
   * @param boardName the new name of the board
   */
  public void setBoardName(String boardName) { this.boardName.setText(boardName); }

  /**
   * Get the board's id.
   *
   * @return the board's id
   */
  public long getBoardId() { return this.id; }

  /**
   * Set the id of the board.
   *
   * @param id the new id of the board
   */
  public void setId(long id) { this.id = id; }
}
