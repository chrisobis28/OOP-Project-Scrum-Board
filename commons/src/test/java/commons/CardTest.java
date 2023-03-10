package commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CardTest {

  @Test
  public void checkConstructor(){
    var a = new Card("card-name", "description");
    assertEquals("card-name", a.cardName);
    assertEquals("description", a.cardDescription);
    var b = new Card("a", "something");
    assertEquals("a", b.cardName);
  }

  @Test
  public void cardNameSetter(){
    var a = new Card("card-name", "description");
    a.setCardName("card");
    assertEquals("card", a.cardName);
  }

  @Test
  public void cardDescriptionSetter(){
    var a = new Card("card-name", "description");
    a.setCardDescription("desc");
    assertEquals("desc", a.cardDescription);
  }

  @Test
  public void equalsHashCode(){
    var a = new Card("a", "b");
    var b = new Card("a", "b");
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  public void notEqualsHashCode(){
    var a = new Card("a", "b");
    var b = new Card("a", "c");
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
