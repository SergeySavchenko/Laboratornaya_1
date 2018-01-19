package com.sergey.savchenko.model;

import java.util.*;

/**
 * class "Tasks", sets methods for work with list of tasks
 *
 * @author SergeySavchenko
 */
public class Tasks {

    /**
     * method for defining tasks that will be executed during specific
     * period of time
     *
     * @param tasks list of tasks for checking
     * @param start start time of the specific period
     * @param end   end time of the specific period
     * @return list of tasks that meet the conditions
     */
    public static Iterable<Task> 
                         incoming(Iterable<Task> tasks, Date start, Date end) throws IllegalArgumentException,
                                                                        InstantiationException, IllegalAccessException {
        String message = "End time can't be less or equal start time";
        if (!end.after(start))
            throw new IllegalArgumentException(message);
        Collection<Task> colList = null;
        TaskList taskList = null;
        Class clazz = tasks.getClass();
        try {
            if (tasks instanceof Collection) {
                colList = (Collection<Task>) clazz.newInstance();
            } else if (tasks instanceof TaskList) {
                taskList = (TaskList) clazz.newInstance();
            }
        } catch (InstantiationException e) {
            throw new InstantiationException("Instantiation exception");
        } catch (IllegalAccessException e) {
            throw new IllegalAccessException("Illegal access");
        }
        for (Task task : tasks) {
            if ((task.nextTimeAfter(start) != null)
                    && (!task.nextTimeAfter(start).after(end))) {
                if (colList != null) {
                    colList.add(task);
                } else if (taskList != null) {
                    taskList.add(task);
                }
            }
        }
        return ((colList != null) ? colList : taskList);
    }
    
    /**
     * method for defining tasks that will be executed in specific
     * period of time
     *
     * @param tasks list of tasks
     * @param start start time of the specific period
     * @param end   end time of the specific period
     * @return collection that meet the conditions
     */
    public static SortedMap<Date, Set<Task>>
                         calendar(Iterable<Task> tasks, Date start, Date end) throws IllegalAccessException,
                                                                                                InstantiationException {
        SortedMap<Date, Set<Task>> sortedMap = new TreeMap<>();
        Iterable<Task> incomingList = Tasks.incoming(tasks, start, end);
        Date tempDate = null;
        for (Task task : incomingList) {
            if (task.isRepeated()) {
                tempDate = task.nextTimeAfter(start);
                while ((tempDate != null) && !tempDate.after(end)) {
                    if (sortedMap.containsKey(tempDate)) {
                        sortedMap.get(tempDate).add(task);
                    } else {
                        Set<Task> set = new HashSet<>();
                        set.add(task);
                        sortedMap.put(tempDate, set);
                    }
                    tempDate = task.nextTimeAfter(tempDate);
                }
            } else {
                if (sortedMap.containsKey(tempDate)) {
                        sortedMap.get(tempDate).add(task);
                    } else {
                        Set<Task> set = new HashSet<>();
                        set.add(task);
                        sortedMap.put(tempDate, set);
                    }
            }
        }
        return sortedMap;
    }
}
