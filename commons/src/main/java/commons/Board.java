

package commons;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    public ArrayList<Cardlist> cardlistList;
    @OneToMany(mappedBy = "board")
    public Set<Tag> tagList;
    public String description = "", boardName = "", boardBackgroundColour = "#FFFFFF", listsBackgroundColour = "#FFFFFF";

    /**
     * Constructor for the board.
     */
        public Board() {
            cardlistList = new ArrayList<>();
            tagList = new HashSet<>();
        }

    /**
     * Constructor for the board with String parameter
     */
    public Board(String boardName) {
        cardlistList = new ArrayList<>();
        this.boardName = boardName;
        tagList = new HashSet<>();
    }

        /**
         * Gets the cardlistList.size() of the board.
         * @return the cardlistList.size() of the cardlistList array.
         */
        public int getSize() {
            return cardlistList.size();
        }

        /**
         * Checks if the Cardlist is empty.
         * @return true if it is empty, false if not.
         */
        public boolean isEmpty() {
            return cardlistList.isEmpty();
        }

        /**
         * Returns the implicit head of the Cardlist.
         *
         * @return the first Cardlist.
         */
        public Cardlist getFirst() {
            return cardlistList.get(0);
        }



        /**
         * Add a Cardlist at a specific index of the Cardlist
         *    OBJ1    OBJ2
         *  0       1      2
         * these are the positions where a new Cardlist could be inserted, first being at the front
         * @param index the element to add.
         * Throws Index Out Of Bounds exception when the insetion at the provided
         * index provided is not possible.
         * Throws Runtime exception when l is null.
         */
        public void add(Cardlist l, int index) {
            if(l == null)
                throw new NullPointerException();
            if(index == cardlistList.size()) {
                cardlistList.add(l);
                return;
            }
            if(this.getSize() < index || index < 0)
                throw new IndexOutOfBoundsException();
            for(int i = cardlistList.size(); i > index; i--)
               cardlistList.set(i, cardlistList.get(i - 1));
        }


        /**
         * Add the Cardlist to the end of the board when a position is not specified
         * @param l Cardlist to be inserted
         * Throws Runtime exception when l is null
         */
        public void add(Cardlist l) {
            if(l == null)
                throw new NullPointerException();
            cardlistList.add(l);
        }

    /**
     * Returns all cardlistList on the board
     * @return returns a Cardlist of type Cardlist
     */
        public ArrayList<Cardlist> getAll(){
            return cardlistList;
        }

    /**
     * Returns the Cardlist of the specified index.
     * @param index the index of the desired Cardlist on the board.
     * @return returns the Cardlist at the desired location
     * Throws index out of bounds exception when index is negative, 0 or
     * out of bounds
     */
    public Cardlist getAtIndex(int index){
        if(index < 0 || index >= cardlistList.size())
            throw new IndexOutOfBoundsException();
        return cardlistList.get(index);
    }

    /**
     * Returns a string formatted form of the board, containing all the cardlistList the board has.
     * @return the string the method constructs.
     */
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("Board:" + '\n');
            ArrayList<Cardlist> all = this.getAll();
            int i = 1;
            for(Cardlist card : all) {
                s.append("List on position ");
                s.append(i);
                s.append(" :");
                s.append(all.get(i++));
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
     * Returns the boardName of the board, which is empty by default.
     * @return the boardName of the board.
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * Returns the colour of the board, which is white (FFFFFF in hexadecimal) by default.
     * @return the colour of the board.
     */
    public String getBoardBackgroundColour() {
        return boardBackgroundColour;
    }

    /**
     * Updates the description with the provided string.
     * @param description the new description this board should have.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the boardName with the provided string.
     * @param boardName the new boardName this board should have.
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


    /**
     * Updates the background colour of the board.
     * @param boardBackgroundColour String containing the hexadecimal code of the new color.
     */
    public void setBoardBackgroundColour(String boardBackgroundColour) {
        this.boardBackgroundColour = boardBackgroundColour;
    }

    /**
     * Updates the background colour of the lists the board contains.
     * @param listsBackgroundColour String containing the hexadecimal code of the new color.
     * Why do we keep this on the board? Shouldn't this be on the cardList class?
     * What if the user wants different background colours for lists?
     */
    public void setListsBackgroundColour(String listsBackgroundColour) {
        this.listsBackgroundColour = listsBackgroundColour;
    }

    /**
     * Updates the background colour with the provided string.
     * @param colour the new colour this board should have.
     */
    public void setBackgroundColour(String colour) {
        if(colour.substring(0,1).equals("#"))
            this.boardBackgroundColour = colour;
        else
        {
            this.boardBackgroundColour = "#" + colour;
        }
    }

    /**
     * Boolean method that determines whether two boards are equal or not.
     * @param o the object that we are comparing this to.
     * @return a boolean with the truth value of the equality.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        if (getSize() != board.getSize()) return false;
        if (!Objects.equals(cardlistList, board.cardlistList)) return false;
        if (!Objects.equals(tagList, board.tagList)) return false;
        if (getDescription() != null ? !getDescription().equals(board.getDescription()) : board.getDescription() != null)
            return false;
        if (getBoardName() != null ? !getBoardName().equals(board.getBoardName()) : board.getBoardName() != null) return false;
        return getBoardBackgroundColour() != null ? getBoardBackgroundColour().equals(board.getBoardBackgroundColour()) : board.getBoardBackgroundColour() == null;
    }


    /**
     * hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (cardlistList != null ? cardlistList.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getBoardName() != null ? getBoardName().hashCode() : 0);
        result = 31 * result + (getBoardBackgroundColour() != null ? getBoardBackgroundColour().hashCode() : 0);
        result = 31 * result + getSize();
        return result;
    }

    /**
     * Reorders the list based on a specified arraylist
     * @param order The integer arraylist, containing the first n-1 integers uniquely, denoting the
     * new order of the list (where n is the cardlistList.size() of the list)
     * Throws Runtime exception when the cardlistList.size()s do not match
     * Throws NPE when order is null or contains a null reference
     */
    public void reorder(int[] order){
        if(order.length != cardlistList.size())
            throw new RuntimeException("Unequal sized arrays, cannot reorder.");
        if(order == null)
            throw new NullPointerException();

        ArrayList<Cardlist> copy = cardlistList;
        for(int i = 0; i <= cardlistList.size(); i++){
            cardlistList.set(i, copy.get(order[i]));
        }

    }

    public boolean contains(Cardlist cardlist){
        return cardlistList.contains(cardlist);
    }

   public boolean contains(Card card){
        for(Cardlist cardlist : cardlistList)
            if(cardlist.contains(card))
                return true;
        return false;
    }


}

