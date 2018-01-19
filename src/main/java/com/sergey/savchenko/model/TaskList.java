package com.sergey.savchenko.model;

import java.io.Serializable;

/**
 * abstract class "TaskList", sets abstract methods for working with list
 *
 * @author SergeySavchenko
 */
public abstract class TaskList implements Iterable<Task>,
                                          Serializable, Cloneable {

    /**
     * method for adding task to list
     *
     * @param task task adding to list
     */
    public abstract void add(Task task);

    /**
     * method for deleting task from task list
     *
     * @param task task deleting from list
     * @return result successful(true) or not(false)
     */
    public abstract boolean remove(Task task);

    /**
     * method for defining size of list
     *
     * @return size of list
     */
    public abstract int size();

    /**
     * method for getting task, using it index in list
     *
     * @param index index of task in list
     * @return task using it index
     */
    public abstract Task getTask(int index);
}
