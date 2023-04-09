package client.services;

import client.components.CardList;
import client.utils.ServerUtils;
import commons.Cardlist;
import org.springframework.stereotype.Service;

@Service
public class ComponentsServices {

  private ServerUtils server;

  public ComponentsServices(ServerUtils server) {
    this.server = server;
  }

  public void CardlistSendEdit(long id, Cardlist cardList) {
    var board = server.getBoardById(id);
    for (var cardlist : board.getCardlistList()) {
      if (cardlist.getId() == cardList.getId()) {
        cardlist.setCardlistName(cardList.getCardlistName());
      }
    }
    server.editCardList(cardList);
    server.editBoard(board);
  }
}
