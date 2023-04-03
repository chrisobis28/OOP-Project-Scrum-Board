package server.api;

import commons.Board;
import commons.Card;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class CardlistControllerTest {

    private TestCardlistRepository repo;
    private CardlistControlller cardlistController;

    private Board z;
    private Cardlist a , b , c ;
    private Card x, y;
    @BeforeEach
    public void setup() {
        repo = new TestCardlistRepository();

        cardlistController = new CardlistControlller(repo);
        a = new Cardlist("a");
        b = new Cardlist("b");
        c = new Cardlist("c");
        x = new Card("x", "X");
        y = new Card("y", "y");
        z = new Board("z");
    }

    @Test
    public void ConstructorTest(){
        assertNotEquals(cardlistController, null);
    }

    @Test
    public void emptyListTest(){
        assertEquals(new ArrayList<Cardlist>(), cardlistController.getAll());
    }

    @Test
    public  void testBadCardlist(){
        Cardlist d = new Cardlist("");
        ResponseEntity<Cardlist> responseEntity =  cardlistController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public  void testNullCardlist(){
        Cardlist d = null;
        ResponseEntity<Cardlist> responseEntity =  cardlistController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testFindByBoardID(){
        long ID = z.getId();
        z.add(a);
        assertEquals(a, cardlistController.add(a).getBody());
        assertEquals(List.of(a), cardlistController.getAllByBoardId(ID));
    }

    @Test
    public void testFindNoneByBoardID(){
        long ID = z.getId();
        assertEquals(new ArrayList<Cardlist>(), cardlistController.getAllByBoardId(ID));
    }

    @Test
    public void testFindNegativeID(){
    }

    /**
     * Tests whether the controller returns a BAD_REQUEST when there is no cardlist
     * with the desired ID.
     */
    @Test
    public void testFindMissingCardlist(){
        long ID = z.getId();;
        assertEquals(cardlistController.getAllByBoardId(ID), List.of());
    }

    @Test
    public void testEditNegativeID(){
        //can't really test this since we don't have a getByID right now
    }


    @Test
    public void testEditMissingCardlist() {
        assertEquals(cardlistController.edit(b).getBody(), BAD_REQUEST);
    }

    @Test
    public void testEditCardlist(){
        long ID = z.getId();
        z.add(a);
        assertEquals(a, cardlistController.add(a).getBody());
        assertEquals(cardlistController.getAllByBoardId(ID), List.of(a));
        a.setCardlistName("update");
        cardlistController.edit(a);
        assertEquals(cardlistController.getAllByBoardId(ID).get(0).getCardlistName(), "update");
    }

    @Test
    public void testNoUpdates() throws Exception{
        Thread.sleep(6000);
        assertEquals(cardlistController.getUpdates().getResult(), null );
    }




    @Test
    public void testDeleteNegativeID(){
    }


    @Test
    public void testDeleteMissingCardlist(){
        long ID = b.getId();
        assertEquals(cardlistController.delete(ID).getBody(), BAD_REQUEST);
    }

    @Test
    public void testDeleteCardlist(){

            assertEquals(b, cardlistController.add(b));
            assertEquals(List.of(b), cardlistController.getAll());
            long ID = b.getId();
            assertEquals(b, cardlistController.delete(ID).getBody());
            assertEquals(List.of(), cardlistController.getAll());

    }


    @Test
    public void clearTest(){
        cardlistController.add(a);
        int init = cardlistController.getAll().size();
        cardlistController.clear();
        int fin = cardlistController.getAll().size();
        assertNotEquals(init, fin);
    }

}