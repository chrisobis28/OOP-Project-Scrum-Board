

package commons;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.*;


@Entity
public class Board {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;
  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  public Set<Cardlist> cardlistList;
  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  public Set<Tag> tagList;
  public String description = "", boardName = "", boardBackgroundColour = "#FFFFFF";
  public Boolean isInWorkspace;

  /**
   * Constructor for the board.
   */
  public Board() {
    cardlistList = new HashSet<>();
    tagList = new HashSet<>();
    isInWorkspace = false;
  }

  /**
   * Constructor for the board with String parameter
   */
  public Board(String boardName) {
    cardlistList = new HashSet<>();
    this.boardName = boardName;
    tagList = new HashSet<>();
    isInWorkspace = false;
  }

  /**
   * Add the Cardlist to the end of the board when a position is not specified
   *
   * @param l Cardlist to be inserted
   *          Throws Runtime exception when l is null
   */
  public void add(Cardlist l) {
    if (l == null)
      throw new NullPointerException();
    cardlistList.add(l);
  }

  public Cardlist getAtIndex(int index) {
    if (index < 0 || index >= cardlistList.size())
      throw new IndexOutOfBoundsException();
    Iterator iter = cardlistList.iterator();
    for (int i = 1; i < index; i++)
      iter.next();
    return (Cardlist) iter.next();
  }

  /**
   * Returns a string formatted form of the board, containing all the cardlistList the board has.
   *
   * @return the string the method constructs.
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  /**
   * Return the id of the board.
   *
   * @return Long integer representing the id of the board
   */
  public long getId() {
    return this.id;
  }

  /**
   * Returns the description of the board, which is empty by default.
   *
   * @return the description of the board.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the boardName of the board, which is empty by default.
   *
   * @return the boardName of the board.
   */
  public String getBoardName() {
    return boardName;
  }

  /**
   * Returns the colour of the board, which is white (FFFFFF in hexadecimal) by default.
   *
   * @return the colour of the board.
   */
  public String getBoardBackgroundColour() {
    return boardBackgroundColour;
  }

  /**
   * Returns true if this Board is in the workspace, false otherwise.
   * @return Boolean value describing whether the board is in the workspace
   */
  public Boolean getIsInWorkspace() { return this.isInWorkspace; }

  /**
   * Changes state of the board being in the workspace.
   */
  public void changeWorkspaceState() {isInWorkspace = !isInWorkspace; }

  /**
   * Updates the description with the provided string.
   *
   * @param description the new description this board should have.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Updates the boardName with the provided string.
   *
   * @param boardName the new boardName this board should have.
   */
  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }


  /**
   * Updates the background colour of the board.
   *
   * @param boardBackgroundColour String containing the hexadecimal code of the new color.
   */
  public void setBoardBackgroundColour(String boardBackgroundColour) {
    this.boardBackgroundColour = boardBackgroundColour;
  }

  /**
   * Boolean method that determines whether two boards are equal or not.
   *
   * @param o the object that we are comparing this to.
   * @return a boolean with the truth value of the equality.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Board board = (Board) o;

    if (id != board.id) return false;
    if (!Objects.equals(cardlistList, board.cardlistList)) return false;
    if (!Objects.equals(tagList, board.tagList)) return false;
    if (getDescription() != null ? !getDescription().equals(board.getDescription()) : board.getDescription() != null)
      return false;
    if (getBoardName() != null ? !getBoardName().equals(board.getBoardName()) : board.getBoardName() != null)
      return false;
    if (getBoardBackgroundColour() != null ? !getBoardBackgroundColour().equals(board.getBoardBackgroundColour()) : board.getBoardBackgroundColour() != null)
      return false;
    return true;
  }

  /**
   * Hashcode function
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * Reorders the list based on a specified arraylist
   *
   * @param order The integer arraylist, containing the first n-1 integers uniquely, denoting the
   *              new order of the list (where n is the cardlistList.size() of the list)
   *              Throws Runtime exception when the cardlistList.size()s do not match
   *              Throws NPE when order is null or contains a null reference
   */
  public void reorder(int[] order) {
    if (order.length != cardlistList.size())
      throw new RuntimeException("Unequal sized arrays, cannot reorder.");
    if (order == null)
      throw new NullPointerException();

    ArrayList<Cardlist> aux = new ArrayList<>(cardlistList);
    cardlistList.clear();
    int i = 0;
    while (aux.size() != cardlistList.size()) {
      cardlistList.add(aux.get(order[i]));
      aux.remove(order[i]);
      i++;
    }
  }

  //Moved these methods here because we may need them at some point
  //but right now they are causing errors due to these getters not
  //being linked to fields.

  /*/**
   * Gets the cardlistList.size() of the board.
   * @return the cardlistList.size() of the cardlistList array.
   */
        /*public int getSize() {
            return cardlistList.size();
        }*/

  /*/**
   * Checks if the Cardlist is empty.
   * @return true if it is empty, false if not.
   */
        /*public boolean isEmpty() {
            return cardlistList.isEmpty();
        }*/

  /*/**
   * Returns the implicit head of the Cardlist.
   *
   * @return the first Cardlist of the board.
   */
       /* public Cardlist getFirst() {
            Iterator iter = cardlistList.iterator();
            return (Cardlist) iter.next();
        }*/

}

