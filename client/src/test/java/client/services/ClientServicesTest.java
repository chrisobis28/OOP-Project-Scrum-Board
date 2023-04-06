package client.services;

import client.utils.ServerUtils;
import commons.Board;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServicesTest {

  @Mock
  ServerUtils serverMock;

  @Test
  public void checkBoardInRepo() {
    ClientServices service = new ClientServices(serverMock);
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
    ClientServices service = new ClientServices(serverMock);
    Board board = new Board("name");
    when(serverMock.getBoardById(anyLong())).thenReturn(board);
    doNothing().when(serverMock).editBoard(any());
    service.editBoardNameById(3,"newName");
    assertEquals("newName", board.getBoardName());
  }
}
