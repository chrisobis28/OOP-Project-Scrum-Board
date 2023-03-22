package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  @Test
  public void checkConstructor(){
    var p = new Board("example-name");
    assertEquals("example-name", p.getName());
  }

  @Test
  public void checkBoardNameSetter() {
    var p = new Board("example-name");
    p.setName("example");
    assertEquals("example", p.getName());
  }

  @Test
  public void checkBoardColourSetter(){
    var p = new Board("some-name");
    assertEquals("#FFFFFF", p.getBackgroundColour());
    p.setBackgroundColour("#ABCDEF");
    assertEquals("#ABCDEF", p.getBackgroundColour());
  }

  @Test
  public void checkListColourSetter(){
    var p = new Board("some-name");
    assertEquals("#FFFFFF", p.getBackgroundColour());
    p.setBackgroundColour("#ABCDEF");
    assertEquals("#ABCDEF", p.getBackgroundColour());
  }

  @Test
  public void equalsHashCode(){
    var a = new Board("a");
    var b = new Board("a");
    assertEquals(a,b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void notEqualsHashCode(){
    var a = new Board("a");
    var b = new Board("a");
    a.setBackgroundColour("#AAABBB");
    assertNotEquals(a, b);
    assertNotEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void hasToString(){
    var actual = new Board("board-name").toString();
    assertTrue(actual.contains(Board.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("boardName"));
  }
}
