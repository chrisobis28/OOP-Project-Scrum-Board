<<<<<<< HEAD
package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void getSize() {
    }

    @Test
    void isEmpty() {
    }

    @Test
    void getFirst() {
    }

    @Test
    void getTail() {
    }

    @Test
    void add() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void getAll() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getDescription() {
    }

    @Test
    void getName() {
    }

    @Test
    void getBackgroundColour() {
    }

    @Test
    void setDescription() {
    }

    @Test
    void setName() {
    }

    @Test
    void setBackgroundColour() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }
}
=======
package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTest {

  @Test
  public void checkConstructor(){
    var p = new Board("example-name");
    assertEquals("example-name", p.boardName);
  }

  @Test
  public void checkBoardNameSetter() {
    var p = new Board("example-name");
    p.setBoardName("example");
    assertEquals("example", p.boardName);
  }

  @Test
  public void checkBoardColourSetter(){
    var p = new Board("some-name");
    assertEquals("#FFFFFF", p.boardBackgroundColour);
    p.setBoardBackgroundColour("#ABCDEF");
    assertEquals("#ABCDEF", p.boardBackgroundColour);
  }

  @Test
  public void checkListColourSetter(){
    var p = new Board("some-name");
    assertEquals("#FFFFFF", p.listsBackgroundColour);
    p.setListsBackgroundColour("#ABCDEF");
    assertEquals("#ABCDEF", p.listsBackgroundColour);
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
    a.setListsBackgroundColour("#AAABBB");
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
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
