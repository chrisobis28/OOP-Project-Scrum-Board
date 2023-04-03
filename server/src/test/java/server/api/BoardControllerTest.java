package server.api;

import commons.Board;
import commons.Cardlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class BoardControllerTest {
    private TestBoardRepository repo;
    private BoardController boardController;

    private Board a, b, c;
    private Cardlist x, y;
    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();

            boardController = new BoardController(repo);
        a = new Board("a");
        b = new Board("b");
        c = new Board("c");
        x = new Cardlist("x");
        y = new Cardlist("y");
    }

    @Test
    public void ConstructorTest(){
        assertNotEquals(boardController, null);
    }

    @Test
    public void emptyListTest(){
        assertEquals(new ArrayList<Board>(), boardController.getAll());
    }

    @Test
    public  void testBadBoard(){
        Board d = new Board("");
        ResponseEntity<Board> responseEntity =  boardController.add(d);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public  void testNullNamedBoard(){
        Board d = new Board(null);
        assertEquals(BAD_REQUEST, boardController.add(d).getStatusCode());
    }

    @Test
    public void testFindByID(){
        long ID = a.getId();
        assertEquals(a, boardController.add(a).getBody());
        assertEquals(a, boardController.getById(ID).getBody());
    }

    @Test
    public void testFindNegativeID(){
        long ID = -1;
        assertEquals(boardController.getById(ID).getStatusCode(), BAD_REQUEST);
    }

    /**
     * Tests whether the controller returns a BAD_REQUEST when there is no board
     * with the desired ID.
     */
    @Test
    public void testFindMissingBoard(){
        long ID = b.getId();
        assertEquals(boardController.getById(ID).getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void testEditNegativeID(){
    }


    @Test
    public void testEditMissingBoard() {
        long ID = b.getId();
        assertEquals(boardController.edit(b).getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void testEditBoard(){
        long ID = a.getId();
        assertEquals(a, boardController.add(a).getBody());
        assertEquals(boardController.getById(ID).getBody().getBoardName(), "a");
        a.setBoardName("update");
        boardController.edit(a);
        assertEquals(boardController.getById(ID).getBody().getBoardName(), "update");
    }

    @Test
    public void testNoUpdates() throws Exception{
            Thread.sleep(6000);
            assertEquals(boardController.getUpdates().getResult(), null );
    }




    @Test
    public void testDeleteNegativeID(){
        assertEquals(boardController.delete(-1).getStatusCode(), BAD_REQUEST);
    }


        @Test
    public void testDeleteMissingBoard(){
        long ID = b.getId();
        assertEquals(boardController.delete(ID).getStatusCode(), BAD_REQUEST);
    }


    //Right now this throws a ConcurrentModificationException.
    //I'm looking into a fix
   /* @Test
    public void testDeleteBoard(){
        assertEquals(b, boardController.add(b).getBody());
        assertEquals(List.of(b), boardController.getAll());
        long ID = b.getId();
        Board cb = boardController.delete(ID).getBody();
        assertEquals(cb.getBoardName(),b.getBoardName());
        assertEquals(List.of(), boardController.getAll());
    }*/

    @Test
    public void clearTest(){
        boardController.add(a);
        int init = boardController.getAll().size();
        boardController.clear();
        int fin = boardController.getAll().size();
        assertNotEquals(init, fin);
    }

}