package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {

    @Test
    public void checkConstructor(){
        var a = new Task("description");
        assertEquals("description", a.description);
        assertFalse(a.completed_status);
    }

    @Test
    public void checkDefaultConstructor() {
        var a = new Task();
        assertSame(a.getClass(), (new Task()).getClass());
    }

    @Test
    public void checkStatus(){
        var a = new Task("description");
        a.complete();
        assertTrue(a.completed_status);
    }

    @Test
    public void checkDescriptionSetter(){
        var a = new Task("something");
        a.setDescription("else");
        assertEquals("else", a.description);
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
