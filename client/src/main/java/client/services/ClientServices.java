package client.services;

import client.utils.ServerUtils;
import commons.Board;
import commons.Cardlist;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.springframework.stereotype.Service;

@Service
public class ClientServices {

  private ServerUtils server;

  public ClientServices(ServerUtils server) {
    this.server = server;
  }

  /**
   * Function that checks whether a board with a given name is in the database.
   *
   * @param name The name of the board to be checked
   * @return the board's id if the board is present in the repo, -1 otherwise
   */
  public long boardInRepo(String name) {
    if (server.getBoardList().isEmpty())
      return -1;
    for (Board board : server.getBoardList()) {
      if (board.getBoardName().equals(name))
        return board.getId();
    }
    return -1;
  }

  /**
   * Edit the board with the given id's name and updates it in the database.
   * @param id the id of the board to be updated
   * @param name the new name of the board
   */
  public void editBoardNameById(long id, String name) {
    Board board1 = server.getBoardById(id);
    board1.setBoardName(name);
    server.editBoard(board1);
  }

  /**
   * Adds a cardlist with a given name to the board with the given id.
   * @param name The name of the new cardlist
   * @param boardId The id of the board to be updated
   */
  public void addListToBoardService(String name, long boardId) {
    Board board = server.getBoardById(boardId);
    Cardlist cardlist = new Cardlist(name, board);
    Cardlist saved = server.addCardList(cardlist);
    board.add(saved);
    server.editBoard(board);
  }

  /**
   * Shows an alert with a given exception message.
   * @param e The exception message to be shown.
   */
  public void showNewAlert(Exception e) {
    var alert = new Alert(Alert.AlertType.ERROR);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.setContentText(e.getMessage());
    alert.showAndWait();
  }

  /**
   * Creates a new Board instance with the given name and adds it to the db and to the workspace.
   * @param name The name of the new Board
   * @return The instance of the created board.
   */
  public Board createNewBoard(String name) {
    Board newBoard = new Board(name);
    newBoard.changeWorkspaceState();
    server.addBoard(newBoard);
    return newBoard;
  }

  /**
   * Changes the given Board's workspace state and sends the update to the database.
   * @param board The board to be updated
   */
  public void changeWorkspaceStateService(Board board) {
    board.changeWorkspaceState();
    server.editBoard(board);
  }

  /**
   * Finds and returns the first board that is present in the workspace.
   * @return Board instance of the first board in the workspace, null if there
   * is no board in the workspace.
   */
  public Board getFirstBoardInWorkspaceService() {
    for (Board b : server.getBoardList())
      if (b.getIsInWorkspace()) {
        return b;
      }
    return null;
  }
}
