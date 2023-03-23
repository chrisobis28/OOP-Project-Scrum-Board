package commons;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Board class.
 */
@Entity
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String boardName;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  public Set<Cardlist> cardlistList;

  @OneToMany(mappedBy = "board")
  public Set<Tag> tagList;

  public String boardBackgroundColour = "#FFFFFF";

  public String listsBackgroundColour = "#FFFFFF";

  /**
   * Default board constructor.
   */
  public Board() {}

  /**
   * Creates a new Board with a given name.
   * @param boardName String representing a new board's name
   */
  public Board(String boardName) {
    this.boardName = boardName;
    tagList = new HashSet<>();
    cardlistList = new HashSet<>();
  }

  /**
   * Sets a board's name to a given string.
   * @param boardName String to become the board's new name
   */
  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }

  /**
   * Return the board's name.
   * @return String that represents the board's name
   */
  public String getName() {
    return this.boardName;
  }

  /**
   * Sets the board's background colour to a given colour.
   * @param boardBackgroundColour String that represents the new colour of the board's background.
   */
  public void setBoardBackgroundColour(String boardBackgroundColour) {
    this.boardBackgroundColour = boardBackgroundColour;
  }

  /**
   * Sets the lists' background colour to a given colour.
   * @param listsBackgroundColour String that represents the new colour of the lists' background.
   */
  public void setListsBackgroundColour(String listsBackgroundColour) {
    this.listsBackgroundColour = listsBackgroundColour;
  }

  /**
   * Checks if a given board is the same as this one.
   * @param obj The given board to be compared to
   * @return true if the boards are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  /**
   * Returns a hashcode for the board.
   * @return Integer representing a hashcode for the board
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * Returns this board represented as a String.
   * @return String representation of this board
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
