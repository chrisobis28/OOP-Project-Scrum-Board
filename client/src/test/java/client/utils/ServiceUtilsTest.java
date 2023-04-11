package client.utils;

import commons.Board;
import commons.Card;
import commons.Cardlist;
import commons.Task;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceUtilsTest {

  private ServerUtils server;

  @Mock
  private WebTarget targetMock;
  @Mock
  public Invocation.Builder builderMock;

  @BeforeEach
  public void setup() {
    server = new ServerUtils("http://localhost:8080/", targetMock);

    builderMock=mock(Invocation.Builder.class);

    when(targetMock.path(anyString())).thenReturn(targetMock);
    when(targetMock.request(MediaType.APPLICATION_JSON)).thenReturn(builderMock);
    when(builderMock.accept(MediaType.APPLICATION_JSON)).thenReturn(builderMock);
  }

  @Test
  public void checkServerValidCheck() {
    when(builderMock.get(eq(String.class))).thenReturn("Hello world!");

    assertTrue(server.checkServerValidity());
  }

  @Test
  public void checkAddCard() {
    Card card = new Card();
    when(builderMock.post(any(Entity.class), eq(Card.class))).thenReturn(card);

    server.addCard(card);

    verify(targetMock).path("api/cards");
    verify(builderMock).post(eq(Entity.entity(card, MediaType.APPLICATION_JSON)), eq(Card.class));
  }

  @Test
  public void checkAddCarlist() {
    Cardlist cardlist = new Cardlist();
    when(builderMock.post(any(Entity.class), eq(Cardlist.class))).thenReturn(cardlist);

    server.addCardList(cardlist);

    verify(targetMock).path("api/cardlist");
    verify(builderMock).post(eq(Entity.entity(cardlist, MediaType.APPLICATION_JSON)), eq(Cardlist.class));
  }

  @Test
  public void checkAddTask() {
    Task task = new Task();
    when(builderMock.post(any(Entity.class), eq(Task.class))).thenReturn(task);

    server.addTask(task);

    verify(targetMock).path("api/tasks");
    verify(builderMock).post(eq(Entity.entity(task, MediaType.APPLICATION_JSON)), eq(Task.class));
  }

  @Test
  public void checkAddBoard() {
    Board board = new Board();
    when(builderMock.post(any(Entity.class), eq(Board.class))).thenReturn(board);

    server.addBoard(board);

    verify(targetMock).path("api/boards");
    verify(builderMock).post(eq(Entity.entity(board, MediaType.APPLICATION_JSON)), eq(Board.class));
  }

}
