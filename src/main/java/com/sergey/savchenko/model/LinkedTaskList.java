package com.sergey.savchenko.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * class "LinkedTaskList", works with linked list of tasks
 *
 * @author SergeySavchenko
 */
public class LinkedTaskList extends TaskList {
    private static final long serialVersionUID = 1L;
    private Entry head;
    private int size;

    /**
     * iterator for this collection
     */
    @Override
    public Iterator iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator {

        private Entry current;
        private Entry next;
        private boolean callNext;


        public MyIterator() {
            current = null;
            next = head;
            callNext = true;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Task next() {
            if (next == null)
                throw new NoSuchElementException();
            Task temp = (Task) next.entryGetTask();
            current = next;
            next = next.next;
            callNext = false;
            return temp;
        }

        @Override
        public void remove() {
            if (current == null || callNext)
                throw new IllegalStateException();
            if (current.prev == null && next == null) {
                head = null;
            } else if (current.prev == null) {
                next.prev = null;
                head = next;
            } else if (next == null) {
                current.prev.next = null;
            } else {
                current.prev.next = next;
                next.prev = current.prev;
            }
            callNext = true;
            size--;
        }
    }

    /**
     * method for adding task to linked list
     *
     * @param task task adding to linked list
     */
    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null)
            throw new IllegalArgumentException("Task can't be equal null");
        if (head == null) {
            head = new Entry(null, task, null);
        } else {
            Entry current = head;
            while (current.next != null) {
                current = current.next;
            }
            Entry newLink = new Entry(current, task, null);
            current.next = newLink;
        }
        size++;
    }

    /**
     * method for deleting task from linked list
     *
     * @param task task deleting from linked list
     * @return result successful(true) or not(false)
     */
    @Override
    public boolean remove(Task task) throws  IllegalArgumentException {
        if (task == null)
            throw new IllegalArgumentException("Task can't be equal null");
        boolean result = false;
        Entry current = head;
        while ((current != null) && (!result)) {
            if ((current.prev == null) && (current.next == null)
                    && (current.entryGetTask().getTitle().equals(task.getTitle()))) {
                head = null;
                result = true;
            } else if ((current.prev == null)
                    && (current.entryGetTask().getTitle().equals(task.getTitle()))) {
                current = current.next;
                current.prev = null;
                head = current;
                result = true;
            } else if ((current.next == null)
                    && (current.entryGetTask().getTitle().equals(task.getTitle()))) {
                current.prev.next = null;
                result = true;
            } else if
                    (current.entryGetTask().getTitle().equals(task.getTitle())) {
                current.prev.next = current.next;
                current.next.prev = current.prev;
                result = true;
            }
            current = current.next;
            if (result) size--;
        }
        return result;
    }

    /**
     * method for defining size of linked list
     *
     * @return size of linked list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * method for getting task, using it index in linked list
     *
     * @param index index of task in linked list
     * @return task using it index
     */
    @Override
    public Task getTask(int index) throws ArrayIndexOutOfBoundsException {
        String message = "Unreachable index of the list";
        if ((index < 0) || (index >= size))
            throw new ArrayIndexOutOfBoundsException(message);
        Entry current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.entryGetTask();
    }

    /**
     * method for getting information about linked list(it's empty or not)
     *
     * @return list is empty(true) or not(false)
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * method for printing list to console
     */
    public void printList() {
        Entry current = head;
        if (isEmpty()) System.out.println("Empty list");
        while (current != null) {
            System.out.println(current.entryGetTask());
            current = current.next;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LinkedTaskList tasks = (LinkedTaskList) obj;
        if (size != tasks.size) return false;
        Entry current1 = head;
        Entry current2 = tasks.head;
        for (int i = 0; i < size; i++) {
            if (!current1.entryGetTask().equals(current2.entryGetTask())) {
                return false;
            }
            current1 = current1.next;
            current2 = current2.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (Task t : this) {
            result = prime * result + (t == null ? 0 : t.hashCode());
        }
        result = prime * result + size;
        return result;
    }

    @Override
    public String toString() {
        String s1 = "LinkedTaskList{" + "\n";
        StringBuffer s2 = new StringBuffer();
        s2.append(head.entryGetTask().toString() + "\n");
        String s3 = "}";
        Entry current = head;
        for (int i = 1; i < size; i++) {
            current = current.next;
            s2.append(current.entryGetTask().toString() + "\n");
        }
        return s1 + s2.toString() + s3;
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        return (LinkedTaskList) super.clone();
    }

    /**
     * class "Entry", defines structure of linked list
     */
    private class Entry {
        private Task task;
        public Entry next;
        public Entry prev;

        /**
         * constructor for creating an element of linked list
         *
         * @param prev reference to the previous element
         * @param task  task
         * @param next  reference to the next element
         */
        public Entry(Entry prev, Task task, Entry next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }

        /**
         * method for getting task from entry set
         *
         * @return task
         */
        public Task entryGetTask() {
            return task;
        }
    }
}


