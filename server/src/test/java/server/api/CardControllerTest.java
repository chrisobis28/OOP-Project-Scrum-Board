package server.api;

import commons.Board;
import commons.Card;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CardControllerTest {

    private TestCardRepository repo;
    private CardController cardController;

    private SimpMessagingTemplate messagingTemplate;
    private Board z;
    private Cardlist a , b , c ;
    private Card x, y;
    @BeforeEach
    public void setup() {
        repo = new TestCardRepository();
        messagingTemplate = new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message) {
                return true;
            }

            @Override
            public boolean send(Message<?> message, long timeout) {
                return false;
            }
        });
        cardController = new CardController(repo, messagingTemplate);
        a = new Cardlist("a");
        b = new Cardlist("b");
        c = new Cardlist("c");
        x = new Card("x", "X");
        y = new Card("y", "y");
        z = new Board("z");
    }

    @Test
    public void ConstructorTest(){
        assertNotEquals(cardController, null);
    }

    @Test
    public  void testBadCard(){
        Card d = new Card(null, "");
        ResponseEntity<Card> responseEntity =  cardController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public  void testAddCard(){

        assertEquals(HttpStatus.OK, cardController.add(x).getStatusCode());
    }

    @Test
    public void testFindByID(){
        long ID = x.getId();
        assertEquals(x, cardController.add(x).getBody());
        assertEquals(x, cardController.getCardById(ID).getBody());
    }

    @Test
    public void testFindNoneByID(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.getCardById(ID).getStatusCode());
    }

    @Test
    public void testFindNegativeID(){
        assertEquals(BAD_REQUEST, cardController.getCardById(-1).getStatusCode());
    }




   @Test
    public void testEditMissingCard(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.edit(x).getStatusCode());
    }


    @Test
    public void testEditNegativeID() {
    }

    @Test
    public void testEditCard(){
        long ID = x.getId();
        assertEquals(x, cardController.add(x).getBody());
        assertEquals(cardController.getCardById(ID).getBody().getCardName(), "x");
        x.setCardName("update");
        cardController.edit(x);
        assertEquals(cardController.getCardById(ID).getBody().getCardName(), "update");
    }






    @Test
    public void testDeleteNegativeID(){
        assertEquals(cardController.delete(-1).getStatusCode(), BAD_REQUEST);
    }


    @Test
    public void testDeleteMissingCardlist(){
        long ID = x.getId();
        assertEquals(cardController.delete(ID).getStatusCode(), BAD_REQUEST);
    }

    /*
    @Test
    public void testDeleteCardlist(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.getCardById(ID).getStatusCode());
        assertEquals(x, cardController.add(x).getBody());
        assertEquals(x, cardController.getCardById(ID).getBody());
        assertEquals(x, cardController.delete(ID).getBody());
        assertEquals(BAD_REQUEST, cardController.getCardById(ID).getStatusCode());

    }*/




}