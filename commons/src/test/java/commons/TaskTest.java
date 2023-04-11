package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    @Test
    public void checkConstructors(){
        var a = new Task("description");
        assertEquals("description", a.description);
        assertFalse(a.completed_status);

        var c = new Card();
        var t = new Task("description", c);
        assertEquals(c, t.getCard());
        assertEquals("description", t.getDescription());
    }

    @Test
    public void checkDefaultConstructor() {
        var a = new Task();
        assertSame(a.getClass(), Task.class);
    }

    @Test
    public void checkIDGetter() {
        var a = new Task();
        assertEquals(a.id, a.getId());
    }

    @Test
    public void checkStatus(){
        var a = new Task("description");
        a.complete();
        assertTrue(a.completed_status);
    }

    @Test
    public void checkDescriptionSetterGetter(){
        var a = new Task("something");
        a.setDescription("else");
        assertEquals("else", a.getDescription());
    }

    @Test
    public void checkStatusGetter() {
        var c = new Card();
        var t = new Task("description", c);
        assertFalse(t.isCompleted_status());
    }

    @Test
    public void checkCardGetter() {
        var c = new Card();
        var t = new Task("description", c);
        assertEquals(c, t.getCard());
    }

    @Test
    public void equalsHashCode(){
        var a = new Task("a");
        var b = new Task("a");
        assertEquals(a,b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        var a = new Task("a");
        var b = new Task("a");
        a.setDescription("something-else");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString(){
        var actual = new Task("task-name").toString();
        assertTrue(actual.contains(Task.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("task-name"));
    }
}
