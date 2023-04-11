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
  public void checkConstructor2() {
    var c = new Cardlist();
    var a = new Card("name", "descr", c);
    assertEquals("name", a.getCardName());
    assertEquals(a.getCardlist(), c);
  }

  @Test
  public void cardNameSetter(){
    var a = new Card("card-name", "description");
    a.setCardName("card");
    assertEquals("card", a.cardName);
  }

  @Test
  public void checkDescriptionGetter() {
    var a = new Card("name","desc");
    assertEquals("desc", a.getCardDescription());
  }

  @Test
  public void checkTagGetter() {
    var a = new Card("name", "desc");
    a.getTagList().add(new Tag());
    assertEquals(1, a.getTagList().size());
  }

  @Test
  public void checkTaskGetter() {
    var a = new Card("name", "desc");
    a.getTaskList().add(new Task());
    assertEquals(1, a.getTaskList().size());
  }

  @Test
  public void checkIdGetter() {
    var a = new Card();
    assertEquals(a.id, a.getId());
  }

  @Test
  public void checkCardlistIdGetterSetter() {
    var a = new Card();
    a.setCardlistID((long) 2);
    assertEquals(2, a.getCardlistID());
  }

  @Test
  public void checkPositionSetterGetter() {
    var a = new Card();
    a.setPosition((long) 1);
    assertEquals(1, a.getPosition());
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
  public void checkCardCardlistGetterSetter() {
    var first = new Cardlist();
    var second = new Cardlist();
    var card = new Card("name","descr", first);
    assertEquals(card.getCardlist(), first);
    card.setCardList(second);
    assertEquals(card.getCardlist(), second);
  }

  @Test
  public void hasToString(){
    var actual = new Card("a","descr").toString();
    assertTrue(actual.contains(Card.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("a"));
  }
}
