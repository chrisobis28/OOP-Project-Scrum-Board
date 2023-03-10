package commons;

import java.util.Objects;

public class TaskList {
    private int element;

    private TaskList prev, next;

    // Constructor: Creates a TaskList object with element = e and next = null
    TaskList(int e) {
        this.element = e;
        this.next = null;
        this.prev = null;
    }

    // Constructor: Creates a TaskList object with element = e and next = n
    public TaskList(int e, TaskList prev, TaskList next) {
        this.element = e;
        this.next = next;
        this.prev = prev;

    }

    // This function gets T e as input and sets e as the element of the TaskList
    public void setElement(int e) {
        element = e;
    }

    // This function returns the element variable of the TaskList
    public int getElement() {
        return element;
    }

    // This function gets TaskList n as input and sets the next variable of the current TaskList object as
    // n.
    public void setNext(TaskList n) {
        next = n;
    }

    // This function returns the next TaskList
    public TaskList getNext() {
        return next;
    }

    public void setPrev(TaskList n) {
        prev = n;
    }

    // This function returns the next TaskList
    public TaskList getPrev() {
        return prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskList TaskList = (TaskList) o;

        if (element != TaskList.element) return false;
        if (!Objects.equals(prev, TaskList.prev)) return false;
        return Objects.equals(next, TaskList.next);
    }

    @Override
    public String toString() {
        return "TaskList:" + '\n' + "element = " + element;
    }
}
