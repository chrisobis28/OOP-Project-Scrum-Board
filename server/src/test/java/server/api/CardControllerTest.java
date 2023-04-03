package server.api;

import commons.Board;
import commons.Card;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CardControllerTest {

    private TestCardRepository repo;
    private CardController cardController;

    private Board z;
    private Cardlist a , b , c ;
    private Card x, y;
    @BeforeEach
    public void setup() {
        repo = new TestCardRepository();

        cardController = new CardController(repo);
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
        Card d = new Card("", "");
        ResponseEntity<Card> responseEntity =  cardController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public  void testNullCard(){
        Card d = null;
        ResponseEntity<Card> responseEntity =  cardController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testFindByID(){
        long ID = x.getId();
        assertEquals(x, cardController.add(x).getBody());
        assertEquals(a, cardController.getCardById(ID));
    }

    @Test
    public void testFindNoneByID(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.getCardById(ID));
    }

    @Test
    public void testFindNegativeID(){
        assertEquals(BAD_REQUEST, cardController.getCardById(-1));
    }



    @Test
    public void testEditMissingCard(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.edit(x));
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
        assertEquals(cardController.delete(-1).getBody(), BAD_REQUEST);
    }


    @Test
    public void testDeleteMissingCardlist(){
        long ID = x.getId();
        assertEquals(cardController.delete(ID).getBody(), BAD_REQUEST);
    }

    @Test
    public void testDeleteCardlist(){
        long ID = x.getId();
        assertEquals(BAD_REQUEST, cardController.getCardById(ID));
        assertEquals(x, cardController.add(x));
        assertEquals(x, cardController.getCardById(ID));
        assertEquals(x, cardController.delete(ID).getBody());
        assertEquals(BAD_REQUEST, cardController.getCardById(ID));

    }




}