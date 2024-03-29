package client.services;

import client.scenes.BoardViewCtrl;
import client.utils.ServerUtils;
import commons.Cardlist;
import org.springframework.stereotype.Service;

@Service
public class ComponentsServices {

  private ServerUtils server;

  public ComponentsServices(ServerUtils server) {
    this.server = server;
  }

  /**
   * Updates the given cardlist and its corresponding board in the database after being updated in the client.
   * @param id The id of the board that is being updated
   * @param cardList The cardlist that is being updated
   */
  public void CardlistSendEdit(long id, Cardlist cardList) {
    var board = server.getBoardById(id);
    for (var cardlist : board.getCardlistList()) {
      if (cardlist.getId() == cardList.getId()) {
        cardlist.setCardlistName(cardList.getCardlistName());
        break;
      }
    }
    server.editCardList(cardList);
    server.editBoard(board);
  }

  /**
   * Deletes the given cardlist and updates its corresponding board.
   * @param id The id of the board to be updated
   * @param cardList The list to be deleted
   */
  public void deleteList(long id, Cardlist cardList) {
    for(commons.Card card : server.getCards(cardList.getId())){
      server.deleteCard(card.getId());
    }
    server.deleteCardList(cardList.getId());
    //trigger an edit on the board.
    server.editBoard(server.getBoardById(id));
  }

  /**
   * Adds a card to the given cardlist and refreshes the board afterwards.
   * @param cardList The cardlist that has a new card added to it
   * @param boardViewCtrl The board controller instance of the board to be refreshed
   */
  public void addCardToCardlist(Cardlist cardList, BoardViewCtrl boardViewCtrl) {
    commons.Card cardToAdd = new commons.Card("Name","Description", cardList);
    commons.Card commonCard = server.addCard(cardToAdd);
    commonCard.setPosition((long) server.getCards(cardList.getId()).size()-1);
    server.editCard(commonCard);
//    cardList.addCard(server.getCards(cardList.getId()).size()-1, commonCard);
//    for (var cardlist : server.getBoardById(boardViewCtrl.getId()).getCardlistList()) {
//      if (cardlist.getId() == cardList.getId()) {
//        cardlist.addCard(server.getCards(cardList.getId()).size(), commonCard);
//        break;
//      }
//    }
    this.CardlistSendEdit(boardViewCtrl.getId(), cardList);
  }

}
