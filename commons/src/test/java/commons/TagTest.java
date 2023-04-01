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
    public void checkConstructor(){
        var a = new Tag("name");
        assertEquals("name", a.tagName);
    }

    @Test
    public void checkBackgroundColourSetter(){
        var a = new Tag("something");
        assertEquals("#FFFFFF", a.backgroundColour);
        a.setBackgroundColour("#CCCDDD");
        assertEquals("#CCCDDD", a.backgroundColour);
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
