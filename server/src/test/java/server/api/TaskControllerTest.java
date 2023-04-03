package server.api;


import commons.Board;
import commons.Card;
import commons.Cardlist;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;

public class TaskControllerTest {
    private TestTaskRepository repo;
    private TaskController cardController;

    private Board z;
    private Cardlist a ;
    private Card x, y;

    private Task t, s;
    @BeforeEach
    public void setup() {
        repo = new TestTaskRepository();

        TaskController taskController= new TaskController(repo);
        a = new Cardlist("a");
        x = new Card("x", "X");
        y = new Card("y", "y");
        z = new Board("z");
        t = new Task("t");
        s = new Task("s");
    }
}
