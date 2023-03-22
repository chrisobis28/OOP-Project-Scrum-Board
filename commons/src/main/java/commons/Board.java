

package commons;

import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Entity
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    private ArrayList<Cardlist> Cardlists;
    @OneToMany(mappedBy = "board")
    public Set<Tag> tagList;
    private String description = "", name = "", backgroundColour = "#FFFFFF";
    private int size = 0;

    /**
     * Constructor for the board.
     */
        public Board() {
            Cardlists = new ArrayList<>();
        }

    /**
     * Constructor for the board with String parameter
     */
    public Board(String name) {
        Cardlists = new ArrayList<>();
        this.name = name;
    }

        /**
         * Gets the size of the board.
         * @return the size of the Cardlists array.
         */
        public int getSize() {
            return size;
        }

        /**
         * Checks if the Cardlist is empty.
         * @return true if it is empty, false if not.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Returns the implicit head of the Cardlist.
         *
         * @return the first Cardlist.
         */
        public Cardlist getFirst() {
            return Cardlists.get(0);
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
            if(index == size) {
                Cardlists.add(l);
                return;
            }
            if(this.getSize() < index || index < 0)
                throw new IndexOutOfBoundsException();
            for(int i = size; i > index; i--)
               Cardlists.set(i, Cardlists.get(i - 1));

        }


        /**
         * Add the Cardlist to the end of the board when a position is not specified
         * @param l Cardlist to be inserted
         * Throws Runtime exception when l is null
         */
        public void add(Cardlist l) {
            if(l == null)
                throw new NullPointerException();
            Cardlists.add(l);
        }

    /**
     * Returns all Cardlists on the board
     * @return returns a Cardlist of type Cardlist
     */
        public ArrayList<Cardlist> getAll(){
            return Cardlists;
        }

    /**
     * Returns the Cardlist of the specified index.
     * @param index the index of the desired Cardlist on the board.
     * @return returns the Cardlist at the desired location
     * Throws index out of bounds exception when index is negative, 0 or
     * out of bounds
     */
    public Cardlist get(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return Cardlists.get(index);
    }

    /**
     * Returns a string formatted form of the board, containing all the Cardlists the board has.
     * @return the string the method constructs.
     */
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("Board:" + '\n');
            ArrayList<Cardlist> all = this.getAll();
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

        if (id != board.id) return false;
        if (getSize() != board.getSize()) return false;
        if (!Objects.equals(Cardlists, board.Cardlists)) return false;
        if (!Objects.equals(tagList, board.tagList)) return false;
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (Cardlists != null ? Cardlists.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBackgroundColour() != null ? getBackgroundColour().hashCode() : 0);
        result = 31 * result + getSize();
        return result;
    }

    /**
     * Reorders the list based on a specified arraylist
     * @param order The integer arraylist, containing the first n-1 integers uniquely, denoting the
     * new order of the list (where n is the size of the list)
     * Throws Runtime exception when the sizes do not match
     * Throws NPE when order is null or contains a null reference
     */
    public void reorder(int[] order){
        if(order.length != size)
            throw new RuntimeException("Unequal sized arrays, cannot reorder.");
        if(order == null)
            throw new NullPointerException();

        ArrayList<Cardlist> copy = Cardlists;
        for(int i = 0; i <= size; i++){
            Cardlists.set(i, copy.get(order[i]));
        }

    }


}

