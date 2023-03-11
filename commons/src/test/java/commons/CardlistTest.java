package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardlistTest {

    @Test
    public void checkConstructor(){
        var p = new Cardlist("example-name");
        assertEquals("example-name", p.cardlistName);
    }

    @Test
    public void cardlistNameSetter(){
        var a = new Cardlist("name");
        a.setCardlistName("something-else");
        assertEquals("something-else", a.cardlistName);
    }

    @Test
    public void equalsHashCode(){
        var a = new Cardlist("a");
        var b = new Cardlist("a");
        assertEquals(a,b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        var a = new Cardlist("a");
        var b = new Cardlist("a");
        a.setCardlistName("b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEquals(){
        var a = new Cardlist("a");
        var b = new Cardlist("a");
        a.cardSet.add(new Card("a", "b"));
        assertNotEquals(a, b);
    }

    @Test
    public void hasToString(){
        var actual = new Cardlist("cardlistName").toString();
        assertTrue(actual.contains(Cardlist.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("cardlistName"));
    }
}
