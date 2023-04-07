package client.components;

import client.scenes.BoardViewCtrl;
import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardTest {

  @Mock
  ServerUtils serverMock;
  @Mock
  commons.Card cardMock;
  @Mock
  CardList cardListMock;
  @Mock
  BoardViewCtrl boardViewCtrlMock;
  Card card;

  @BeforeEach
  public void setup() {
    this.card = new Card(boardViewCtrlMock, serverMock, cardMock, cardListMock, false);
  }

  @Test
  public void checkCardlistGetter() {
    assertNotNull(card.getCardList());
  }

  @Test
  public void checkSendEdit() {
    doNothing().when(serverMock).editCard(any());
    doNothing().when(cardListMock).sendEdit();
    card.sendEdit();
    verify(serverMock, times(1)).editCard(any());
    verify(cardListMock, times(1)).sendEdit();
  }

  @Test
  public void checkGetCard() {
    assertNotNull(card.getCard());
  }

}
