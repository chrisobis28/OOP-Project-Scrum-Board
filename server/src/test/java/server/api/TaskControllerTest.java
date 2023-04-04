package server.api;


import commons.Board;
import commons.Card;
import commons.Cardlist;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class TaskControllerTest {
    private TestTaskRepository repo;
    private TaskController taskController;

    private Board z;
    private Cardlist a ;
    private Card x, y;

    private Task t, s;
    @BeforeEach
    public void setup() {
        repo = new TestTaskRepository();

        taskController= new TaskController(repo);
        a = new Cardlist("a");
        x = new Card("x", "X");
        y = new Card("y", "y");
        z = new Board("z");
        t = new Task("t");
        s = new Task("s");
    }

    @Test
    public void testAddAll(){
        taskController.add(t);
        taskController.add(s);
        assertEquals(taskController.getAll(), List.of(t, s));
    }

    @Test
    public void testGetNegativeID(){
        assertEquals(taskController.getById(-1).getStatusCode(), BAD_REQUEST);
    }


    @Test
    public void testGetMissingCardlist(){
        long ID = x.getId();
        assertEquals(taskController.getById(ID).getStatusCode(), BAD_REQUEST);
    }

    @Test
    public void testAddNullDescriptionTask(){
        Task w = new Task(null);
        assertEquals(BAD_REQUEST, taskController.add(w).getStatusCode());

    }

    @Test
    public void testAddNullTask(){
        Task w = null;
        assertEquals(BAD_REQUEST, taskController.add(w).getStatusCode());
    }
}
