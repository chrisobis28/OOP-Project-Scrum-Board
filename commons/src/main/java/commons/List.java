package commons;

import java.util.Objects;

public class List {
    private int element;

    private List prev, next;

    // Constructor: Creates a List object with element = e and next = null
    List(int e) {
        this.element = e;
        this.next = null;
        this.prev = null;
    }

    // Constructor: Creates a List object with element = e and next = n
    public List(int e, List prev, List next) {
        this.element = e;
        this.next = next;
        this.prev = prev;

    }

    // This function gets T e as input and sets e as the element of the List
    public void setElement(int e) {
        element = e;
    }

    // This function returns the element variable of the List
    public int getElement() {
        return element;
    }

    // This function gets List n as input and sets the next variable of the current List object as
    // n.
    public void setNext(List n) {
        next = n;
    }

    // This function returns the next List
    public List getNext() {
        return next;
    }

    public void setPrev(List n) {
        prev = n;
    }

    // This function returns the next List
    public List getPrev() {
        return prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        List list = (List) o;

        if (element != list.element) return false;
        if (!Objects.equals(prev, list.prev)) return false;
        return Objects.equals(next, list.next);
    }

    @Override
    public String toString() {
        return "List:" + '\n' + "element = " + element;
    }
}
