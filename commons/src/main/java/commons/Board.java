<<<<<<< HEAD

package commons;

import org.jetbrains.annotations.Contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Objects;

@Entity
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @SuppressWarnings("JpaAttributeTypeInspection")
    private CardList tail, head;
    private String description = "", name = "", backgroundColour = "#FFFFFF";
    private int index, size;

    /**
     * Constructor for the board. It utilises a Doubly-Linked-List structure to keep all the CardLists
     */
        public Board() {
            this.tail = null;
            this.head = null;
        }

        /**
         * Gets the size of the board.
         * @return the size of the CardList.
         */
        public int getSize() {
            if (tail == null) return 0;
            int size = 1;
            CardList currentList = this.tail.getNext();
            while (currentList != this.tail) {
                size++;
                currentList = currentList.getNext();
            }
            return size;
        }

        /**
         * Checks if the CardList is empty.
         * @return true if it is empty, false if not.
         */
        public boolean isEmpty() {
            return this.tail == null;
        }

        /**
         * Returns the implicit head of the CardList.
         * @return the first CardList.
         */
        public CardList getFirst() {
            if (this.tail == null) {
                return null;
            }
            return tail.getNext();
        }

        /**
         * Returns the tail of the CardList.
         * @return the last element.
         */
        public CardList getTail() {
            if (this.isEmpty()) {
                return null;
            }
            return tail;
        }

        /**
         * Add a CardList at a specific index of the CardList, 1 indexed
         *    OBJ1    OBJ2
         *  1       2      3
         * these are the positions where a new CardList could be inserted, first being at the front
         * @param index the element to add.
         * Throws Runtime exception when l is null
         */
        public void add(CardList l, int index) {
            if(l == null)
                throw new NullPointerException();
            if (this.head.getNext().equals(tail)) {
                if(index != 1)
                    throw new IndexOutOfBoundsException();
                head.setNext(l);
               l.setPrev(head);
               l.setNext(tail);
               tail.setPrev(l);
                return;
            }
            if(this.getSize() <= index)
                throw new IndexOutOfBoundsException();
            CardList current = head;
            for(int i = 1; i < index; i++)
                current = current.getNext();
            head.setNext(l);
            l.setPrev(head);
            l.setNext(tail);
            tail.setPrev(l);
        }


        /**
         * Add the CardList to the end of the board when not specified a position
         * @param l CardList to be inserted
         * Throws Runtime exception when l is null
         */
        public void add(CardList l) {
            if(l == null)
                throw new NullPointerException();

            CardList prev = tail.getPrev();
            prev.setNext(l);
            l.setPrev(prev);
            l.setNext(tail);
            tail.setPrev(l);
        }

    /**
     * Returns all CardLists on the board
     * @return returns a CardList of type CardList
     */
        public ArrayList<CardList> getAll(){
            ArrayList<CardList> all = new ArrayList<>();
            CardList l = head.getNext();
            while(!l.equals(tail)) {
                all.add(l);
            }
            return all;
        }

    /**
     * Returns a string formatted form of the board, containing all the CardLists the board has.
     * @return the string the method constructs.
     */
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("Board:" + '\n');
            ArrayList<CardList> all = this.getAll();
            for(int i = 0; i < all.size(); i++) {
                s.append("List on position ");
                s.append(i);
                s.append(" :");
                s.append(all.get(i));
                s.append('\n');
            }
            return s.toString();
        }

    /**
     * Returns the description of the board, which is empty by default.
     * @return the description of the board.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the board, which is empty by default.
     * @return the name of the board.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the colour of the board, which is white (FFFFFF in hexadecimal) by default.
     * @return the colour of the board.
     */
    public String getBackgroundColour() {
        return backgroundColour;
    }

    /**
     * Updates the description with the provided string.
     * @param description the new description this board should have.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the name with the provided string.
     * @param name the new name this board should have.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the background colour with the provided string.
     * @param colour the new colour this board should have.
     */
    public void setBackgroundColour(String colour) {
        if(colour.substring(0,1).equals("#"))
            this.backgroundColour = colour;
        else
        {
            this.backgroundColour = "#" + colour;
        }
    }

    /**
     * Boolean method that determines whether two boards are equal or not.
     * @param o the object that we are comparing this to.
     * @return a boolean with the truth value of the equality.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (getTail() != null ? !getTail().equals(board.getTail()) : board.getTail() != null) return false;
        if (!Objects.equals(head, board.head)) return false;
        if (getDescription() != null ? !getDescription().equals(board.getDescription()) : board.getDescription() != null)
            return false;
        if (getName() != null ? !getName().equals(board.getName()) : board.getName() != null) return false;
        return getBackgroundColour() != null ? getBackgroundColour().equals(board.getBackgroundColour()) : board.getBackgroundColour() == null;
    }

    /**
     * hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = getTail() != null ? getTail().hashCode() : 0;
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBackgroundColour() != null ? getBackgroundColour().hashCode() : 0);
        return result;
    }

    /**
     * Reorders the list based on a specified arraylist
     * @param order The integer arraylist, containing the first n-1 integers uniquely, denoting the
     *              new order of the list (where n is the size of the list)
     * Throws Runtime exception when the sizes do not match
     * Throws NPE when order contains a null reference
     */
    public void reorder(ArrayList<Integer> order){
        if(order.size() != this.getSize())
            throw new RuntimeException("Unequal sized arrays.");
        if(order.contains(null))
            throw new NullPointerException();
        ArrayList<CardList> all = new ArrayList<>();
        CardList cl = head.getNext();
        while(!cl.equals(tail)){
            all.add(cl);
            cl = cl.getNext();
        }

    }

    /**
     * Returns the CardList of the specified index.
     * @param index the index of the desired CardList on the board.
     * @return returns the CardList at the desired location
     * Throws index out of bounds exception when index is negative, 0 or
     * out of bounds
     */
    public CardList getAtIndex(int index){
        if(index < 1 || index > this.getSize())
            throw new IndexOutOfBoundsException();
        CardList cl = head.getNext();
        for(int i = 1; i < index; i++){
            cl = cl.getNext();
        }
        return cl;
    }
}


=======
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

  public Board(){

  }

  public Board(String boardName){
    this.boardName = boardName;
    tagList = new HashSet<>();
    cardlistList = new HashSet<>();
  }

  public void setBoardName(String boardName) {
    this.boardName = boardName;
  }


  public void setBoardBackgroundColour(String boardBackgroundColour) {
    this.boardBackgroundColour = boardBackgroundColour;
  }

  public void setListsBackgroundColour(String listsBackgroundColour) {
    this.listsBackgroundColour = listsBackgroundColour;
  }

  @Override
  public boolean equals(Object obj){
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode(){
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public String toString(){
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
>>>>>>> 9cf2c8ed802b0720102e891c15f6150df6e242e9
