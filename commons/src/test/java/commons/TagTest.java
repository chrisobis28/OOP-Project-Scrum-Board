package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagTest {

    @Test
    public void checkDefaultConstructor() {
        var t = new Tag();
        assertTrue(t instanceof Tag);
    }

    @Test
    public void checkBoardSetterGetter() {
        var t = new Tag();
        Board b = new Board();
        t.setBoard(b);
        assertEquals(b,t.getBoard());
    }

    @Test
    public void checkTagNameGetter() {
        var t = new Tag("ew");
        assertEquals("ew", t.getTagName());
    }

    @Test
    public void checkConstructor(){
        var a = new Tag("name");
        assertEquals("name", a.tagName);
    }

    @Test
    public void checkBackgroundColourSetterGetter(){
        var a = new Tag("something");
        assertEquals("#FFFFFF", a.getBackgroundColour());
        a.setBackgroundColour("#CCCDDD");
        assertEquals("#CCCDDD", a.getBackgroundColour());
    }

    @Test
    public void checkCardSetterGetter() {
        var a = new Tag();
        var c = new Card();
        a.setCard(c);
        assertEquals(a.getCard(), c);
    }

    @Test
    public void checkFontColourSetter(){
        var a = new Tag("something");
        assertEquals("#000000", a.fontColour);
        a.setFontColour("#BBBBBB");
        assertEquals("#BBBBBB", a.fontColour);
    }

    @Test
    public void equalsHashCode(){
        var a = new Tag("a");
        var b = new Tag("a");
        assertEquals(a,b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        var a = new Tag("a");
        var b = new Tag("b");
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hasToString(){
        var actual = new Tag("tag-name").toString();
        assertTrue(actual.contains(Tag.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("tag-name"));
    }
}
