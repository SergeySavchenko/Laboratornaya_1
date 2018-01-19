package com.sergey.savchenko.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * class "ArrayTaskList", works with array of tasks
 *
 * @author SergeySavchenko
 */
public class ArrayTaskList extends TaskList {
    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CAPACITY = 10;
    private static final float SCALE_FACTOR = 1.5f;
    private Task[] list = new Task[INITIAL_CAPACITY];
    private int size;

    /**
     * iterator for this collection
     */
    @Override
    public Iterator iterator() {
        return new MyIterator(list);
    }

    private class MyIterator implements Iterator {
        private Task[] list;
        private int index;
        private boolean callNext;

        public MyIterator(Task[] list) {
            this.list = list;
            index = 0;
            callNext = true;
        }

        @Override
        public boolean hasNext() {
            return list[index] != null;
        }

        @Override
        public Task next() {
            if (index == list.length || list[index] == null)
                throw new NoSuchElementException("No such element");
            callNext = false;
            return list[index++];
        }

        @Override
        public void remove() {
            if (callNext)
                throw new IllegalStateException("Illegal state");
            for (int i = index - 1; i < list.length - 1; i++) {
                list[i] = list[i + 1];
            }
            list[list.length - 1] = null;
            callNext = true;
            index--;
        }
    }

    /**
     * method for adding task to task array
     *
     * @param task task adding to array
     */
    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null)
            throw new IllegalArgumentException("Task can't be equal null");
        if (list.length > size) {
            list[size] = task;
        } else {
            Task[] newList;
            newList = new Task[(int) (list.length * SCALE_FACTOR)];
            for (int i = 0; i < list.length; i++) {
                newList[i] = list[i];
            }
            list = newList;
            list[size] = task;
        }
        size++;
    }

    /**
     * method for deleting task from task array
     *
     * @param task task deleting from array
     * @return result successful(true) or not(false)
     */
    @Override
    public boolean remove(Task task) throws IllegalArgumentException {
        if (task == null)
            throw new IllegalArgumentException("Task can't be equal null");
        boolean result = false;
        for (int i = 0; i < size; i++) {
            if (list[i].getTitle().equals(task.getTitle())) {
                for (int j = i; j < size - 1; j++) {
                    list[j] = list[j + 1];
                }
                list[size - 1] = null;
                result = true;
                size--;
                break;
            }
        }
        return result;
    }

    /**
     * method for putting task in the specific position in task array
     *
     * @param index index of task in task array
     * @param task task deleting from array
     */
    public void set(int index, Task task) throws IllegalArgumentException {
        if ((task == null) || (index >= size) || (index < 0))
            throw new IllegalArgumentException("Task can't be equal null or invalid index");
        list[index] = task;
    }

    /**
     * method for defining size of task array
     *
     * @return size of task array
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * method for getting task, using it index in array
     *
     * @param index index of task in array
     * @return task using it index
     */
    @Override
    public Task getTask(int index) throws ArrayIndexOutOfBoundsException{
        String message = "Unreachable index of array";
        if ((index < 0) || (index >= list.length))
            throw new ArrayIndexOutOfBoundsException(message);
        return list[index];
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ArrayTaskList tasks = (ArrayTaskList) obj;
        return Arrays.equals(list, tasks.list);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(list);
    }

    @Override
    public String toString() {
        StringBuffer tasks = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null) {
                tasks.append((i + 1) + ". " + list[i] + ";\n");
            }
        }
        if (list[0] != null) {
            tasks.replace(tasks.length() - 2, tasks.length(), ".\n");
        }
        return tasks.toString();
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.list = (Task[]) list.clone();
        return clone;
    }
}
