package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

  @Test
  public void checkConstructor(){
    var p = new Board("example-name");
    assertEquals("example-name", p.boardName);
  }

  @Test
  public void checkEmptyConstructor() {
    Board b = new Board();
    assertEquals(b.getCardlistList().size(),0);
    assertEquals(b.getIsInWorkspace(),false);
  }

  @Test
  public void checkAdd() {
    var p = new Board("example-name");
    p.add(new Cardlist());
    assertEquals(1, p.getCardlistList().size());
    assertThrows(NullPointerException.class, () -> {
      p.add(null);
    });
  }

  @Test
  public void checkDescriptionSetter() {
    var p = new Board("example-name");
    p.setDescription("description");
    assertEquals("description", p.getDescription());
  }

  @Test
  public void checkDescriptionGetter() {
    var p = new Board("example-name");
    p.setDescription("description");
    assertEquals("description", p.getDescription());
  }

  @Test
  public void checkWorkspaceStateChange() {
    var p = new Board("example-name");
    p.changeWorkspaceState();
    assertEquals(true, p.getIsInWorkspace());
  }

  @Test
  public void checkIsInWorkspaceGetter() {
    var p = new Board("example-name");
    assertEquals(false, p.getIsInWorkspace());
    //p.isInWorkspace=null;
    assertEquals(false, p.getIsInWorkspace());
  }

  @Test
  public void checkBoardColorChecker() {
    var p = new Board("some-name");
    assertEquals("#FFFFFF", p.getBoardBackgroundColour());
  }

  @Test
  public void checkIdGetter() {
    var p = new Board();
    assertEquals(p.id, p.getId());
  }

  @Test
  public void checkBoardNameGetter() {
    var p = new Board("example-name");
    assertEquals("example-name", p.getBoardName());
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

//  @Test
//  public void equalsHashCode(){
//    var a = new Board("a");
//    var b = new Board("a");
//    assertEquals(a,b);
//    assertEquals(a.hashCode(), b.hashCode());
//  }

//  @Test
//  public void notEqualsHashCode(){
//    var a = new Board("a");
//    var b = new Board("b");
//    assertNotEquals(a, b);
//    assertNotEquals(a.hashCode(), b.hashCode());
//  }

  @Test
  public void hasToString(){
    var actual = new Board("board-name").toString();
    assertTrue(actual.contains(Board.class.getSimpleName()));
    assertTrue(actual.contains("\n"));
    assertTrue(actual.contains("boardName"));
  }

  @Test
  public void checkEquals() {
    var first = new Board("x");
    var second = new Board("g");
    assertEquals(first,first);
    assertNotEquals(first,second);
  }
}
