package commons;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.lang3.builder.EqualsBuilder;
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
    public void add(Cardlist l) throws  NullPointerException{
        if (l == null)
            throw new NullPointerException();
        cardlistList.add(l);
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
    @Lob
    public long getId() {
        return this.id;
    }

    /**
     * Returns the description of the board, which is empty by default.
     *
     * @return the description of the board.
     */
    @Lob
    public String getDescription() {
        return description;
    }

    /**
     * Returns the boardName of the board, which is empty by default.
     *
     * @return the boardName of the board.
     */
    @Lob
    public String getBoardName() {
        return boardName;
    }

    /**
     * Returns the colour of the board, which is white (FFFFFF in hexadecimal) by default.
     *
     * @return the colour of the board.
     */
    @Lob
    public String getBoardBackgroundColour() {
        return boardBackgroundColour;
    }

    /**
     * Returns true if this Board is in the workspace, false otherwise.
     * @return Boolean value describing whether the board is in the workspace
     */
    public Boolean getIsInWorkspace() {
        //Adding a check in case the board was created before this mr
        if (this.isInWorkspace == null) {
            this.isInWorkspace = false;
        }
        return this.isInWorkspace;
    }

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
     * Getter for the list of cardlists in the board.
     *
     * @return list of all the cardlists.
     */
    @JsonManagedReference
    public Set<Cardlist> getCardlistList() {
        return cardlistList;
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
        return EqualsBuilder.reflectionEquals(this, o);
    }
//
//  /**
//   * Hashcode function
//   *
//   * @return hashcode
//   */
//  @Override
//  public int hashCode() {
//    return HashCodeBuilder.reflectionHashCode(this);
//  }


    //Moved these methods here because we may need them at some point
    //but right now they are either causing errors due to these getters not
    //being linked to fields, or not being used.

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

//  /**
//   * Reorders the list based on a specified arraylist
//   *
//   * @param order The integer arraylist, containing the first n-1 integers uniquely, denoting the
//   *              new order of the list (where n is the cardlistList.size() of the list)
//   *              Throws Runtime exception when the cardlistList.size()s do not match
//   *              Throws NPE when order is null or contains a null reference
//   */
//  public void reorder(int[] order) {
//    if (order.length != cardlistList.size())
//      throw new RuntimeException("Unequal sized arrays, cannot reorder.");
//
//    ArrayList<Cardlist> aux = new ArrayList<>(cardlistList);
//    cardlistList.clear();
//    int i = 0;
//    while (aux.size() != cardlistList.size()) {
//      cardlistList.add(aux.get(order[i]));
//      aux.remove(order[i]);
//      i++;
//    }
//  }

//  public Cardlist getAtIndex(int index) {
//    if (index < 0 || index >= cardlistList.size())
//      throw new IndexOutOfBoundsException();
//    Iterator iter = cardlistList.iterator();
//    for (int i = 1; i < index; i++)
//      iter.next();
//    return (Cardlist) iter.next();
//  }
}
