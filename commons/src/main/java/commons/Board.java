package commons;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Objects;

public class Board {


        /**
         * The circularly linked list class. Note that there is only a tail field.
         * The size can be obtained in O(N) via the getSize() method.
         */
        private List tail, head;
        private String description = "", name = "", backgroundColour = "#FFFFFF";
    private List l;
    private int index;

    /**
     * Constructor for the board. It utilises a Doubly-Linked-List structure to keep all the lists
     */
        public Board() {
            this.tail = null;
            this.head = null;
        }

        /**
         * Gets the size of the board.
         * @return the size of the list.
         */
        public int getSize() {
            if (tail == null) return 0;
            int size = 1;
            List currentList = this.tail.getNext();
            while (currentList != this.tail) {
                size++;
                currentList = currentList.getNext();
            }
            return size;
        }

        /**
         * Checks if the list is empty.
         * @return true if it is empty, false if not.
         */
        public boolean isEmpty() {
            return this.tail == null;
        }

        /**
         * Returns the implicit head of the list.
         * @return the first List.
         */
        public List getFirst() {
            if (this.tail == null) {
                return null;
            }
            return tail.getNext();
        }

        /**
         * Returns the tail of the list.
         * @return the last element.
         */
        public List getTail() {
            if (this.isEmpty()) {
                return null;
            }
            return tail;
        }

        /**
         * Add a list at a specific index of the list, 1 indexed
         *    OBJ1    OBJ2
         *  1       2      3
         * these are the positions where a new list could be inserted, first being at the front
         * @param index the element to add.
         */
        public void add(List l, int index) {
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
            List current = head;
            for(int i = 1; i < index; i++)
                current = current.getNext();
            head.setNext(l);
            l.setPrev(head);
            l.setNext(tail);
            tail.setPrev(l);
        }


        /**
         * Add the list to the end of the board when not specified a position
         * @param l List to be inserted
         */
        public void add(List l) {
            List prev = tail.getPrev();
            prev.setNext(l);
            l.setPrev(prev);
            l.setNext(tail);
            tail.setPrev(l);
        }

    /**
     * Returns all Lists on the board
     * @return returns a list of type List
     */
        public ArrayList<List> getAll(){
            ArrayList<List> all = new ArrayList<>();
            List l = head.getNext();
            while(!l.equals(tail)) {
                all.add(l);
            }
            return all;
        }

    /**
     * Returns a string formatted form of the board, containing all the lists the board has.
     * @return the string the method constructs.
     */
        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("Board:" + '\n');
            ArrayList<List> all = this.getAll();
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
}
