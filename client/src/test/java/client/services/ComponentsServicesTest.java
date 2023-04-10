package client.services;

import client.utils.ServerUtils;
import commons.Board;
import commons.Card;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComponentsServicesTest {

  @Mock
  private ServerUtils serverMock;
  private ComponentsServices service;
  private Board board;
  private Cardlist cardlist1, cardlist2;
  private Card card1;

  @BeforeEach
  public void setup() {
    service = new ComponentsServices(serverMock);
    board = new Board();
    cardlist1 = new Cardlist();
    cardlist2 = new Cardlist();
    card1 = new Card();
    cardlist1.addCard(cardlist1.getCardSet().size(),card1);
    board.add(cardlist1);
    board.add(cardlist2);
  }

  @Test
  public void checkCardlistSentEdit() {
    when(serverMock.getBoardById(anyLong())).thenReturn(board);
    doNothing().when(serverMock).editCardList(any());
    doNothing().when(serverMock).editBoard(any());

    service.CardlistSendEdit(1, cardlist1);

    //Check whether methods were called on the mock object
    verify(serverMock).getBoardById(anyLong());
    verify(serverMock).editBoard(any());
    verify(serverMock).editCardList(any());
  }

  @Test
  public void checkDeleteList() {
    doNothing().when(serverMock).deleteCardList(anyLong());
    doNothing().when(serverMock).deleteCard(anyLong());
    doNothing().when(serverMock).editBoard(any());
    when(serverMock.getBoardById(anyLong())).thenReturn(board);

    service.deleteList(1, cardlist1);

    verify(serverMock).deleteCardList(cardlist1.getId());
    verify(serverMock).deleteCard(card1.getId());
    verify(serverMock).editBoard(board);
  }
}
