package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardlistTest {

    @Test
    public void checkConstructor(){
        var p = new Cardlist("example-name");
        assertEquals("example-name", p.getCardlistName());
    }

    @Test
    public void checkConstructor2() {
        var p = new Cardlist(3, "name");
        assertEquals(3, p.getId());
        assertEquals("name", p.getCardlistName());
    }

    @Test
    public void checkConstructor3() {
        var b = new Board();
        var p = new Cardlist("name", b);
        assertEquals(b, p.getBoard());
        assertEquals("name", p.getCardlistName());
    }

    @Test
    public void checkIdGetter() {
        var b = new Cardlist(3, "name");
        assertEquals(3, b.getId());
    }

    @Test
    public void checkCardAddRemoveGetter() {
        var b = new Cardlist();
        var c = new Card();
        b.addCard(c);
        assertEquals(1, b.getCardSet().size());
        b.removeCard(c);
        assertEquals(0, b.getCardSet().size());
    }

    @Test
    public void cardlistNameSetter(){
        var a = new Cardlist("name");
        a.setCardlistName("something-else");
        assertEquals("something-else", a.getCardlistName());
    }

//    @Test
//    public void equalsHashCode(){
//        var a = new Cardlist("a");
//        var b = new Cardlist("a");
//        assertEquals(a,b);
//        assertEquals(a.hashCode(), b.hashCode());
//    }

//    @Test
//    public void notEqualsHashCode(){
//        var a = new Cardlist("a");
//        var b = new Cardlist("a");
//        a.setCardlistName("b");
//        assertNotEquals(a, b);
//        assertNotEquals(a.hashCode(), b.hashCode());
//    }

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
