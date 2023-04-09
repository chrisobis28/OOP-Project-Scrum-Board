package client.services;

import client.utils.ServerUtils;
import commons.Board;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {

  @Mock
  ServerUtils serverMock;

  ClientServices service;

  @BeforeEach
  public void setup() {
    service = new ClientServices(serverMock);
  }

  @Test
  public void checkBoardInRepo() {
    List<Board> boardList = new ArrayList<>();
    when(serverMock.getBoardList()).thenReturn(boardList);
    //test empty server list
    assertEquals(-1, service.boardInRepo("name"));
    boardList.add(new Board("board"));
    //test board not in list
    assertEquals(-1, service.boardInRepo("name"));
    Board goodBoard = new Board("name");
    boardList.add(goodBoard);
    assertEquals(goodBoard.getId(), service.boardInRepo("name"));
  }

  @Test
  public void checkEditBoardNameById() {
    Board board = new Board("name");
    when(serverMock.getBoardById(anyLong())).thenReturn(board);
    service.editBoardNameById(3,"newName");
    assertEquals("newName", board.getBoardName());
  }

  @Test
  public void checkAddListToBoard() {
    Board board = new Board("name");
    Cardlist cardlist = new Cardlist("name", board);
    doNothing().when(serverMock).editBoard(any());
    when(serverMock.getBoardById(anyLong())).thenReturn(board);
    when(serverMock.addCardList(any())).thenReturn(cardlist);
    service.addListToBoardService("name", 3);
    assertTrue(board.getCardlistList().contains(cardlist));

    //Check whether methods were called on the mock object
    verify(serverMock).getBoardById(anyLong());
    verify(serverMock).addCardList(any());
    verify(serverMock).editBoard(any());
  }

  @Test
  public void checkCreateNewBoard() {
    when(serverMock.addBoard(any())).thenReturn(new Board());
    Board board = service.createNewBoard("great");
    assertEquals(false, board.getIsInWorkspace());

    //Check whether methods were called on the mock object
    verify(serverMock).addBoard(any());
  }

  @Test
  public void checkWorkspaceStateChange() {
    Board board = new Board("board");
    doNothing().when(serverMock).editBoard(any());
    service.changeWorkspaceStateService(board);
    assertEquals(true, board.getIsInWorkspace());

    //Check whether methods were called on the mock object
    verify(serverMock).editBoard(any());
  }

  @Test
  public void checkGetFirstBoardInWorkspace() {
    List<Board> boards = new ArrayList<>();
    when(serverMock.getBoardList()).thenReturn(boards);

    //check that empty list of boards returns a null
    assertNull(service.getFirstBoardInWorkspaceService());

    //check that a list with no boards in the workspace returns null
    boards.add(new Board("name"));
    assertNull(service.getFirstBoardInWorkspaceService());

    //check whether a list with a boards in the workspace correctly returns the first one.
    Board goodBoard = new Board("good");
    goodBoard.changeWorkspaceState();
    boards.add(goodBoard);
    Board secondBoard = new Board("fine");
    secondBoard.changeWorkspaceState();
    boards.add(secondBoard);
    assertEquals(goodBoard, service.getFirstBoardInWorkspaceService());
  }
}
