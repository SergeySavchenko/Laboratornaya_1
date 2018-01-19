package com.sergey.savchenko.controller;

import com.sergey.savchenko.model.TmModel;
import com.sergey.savchenko.model.Task;
import com.sergey.savchenko.view.*;

import java.util.Date;

/**
 * class "TmControllerAdd", class that connects view adding tasks classes with adding tasks methods and
 * other way round
 *
 * Created by 2017 on 17.01.2018.
 */
public class TmControllerAdd {
    private static TmControllerAdd instance;
    private TmControllerMain controllerMain;
    private TmModel model;

    /**
     * constructor for creating object of TmControllerAdd class
     *
     */
    public TmControllerAdd() {
        if (controllerMain == null) {
            controllerMain = TmControllerMain.getInstance();
            model = controllerMain.getModel();
        }
    }

    /**
     * method for getting instance of TmControllerAdd class
     *
     * @return instance of TmControllerAdd class
     */
    public static TmControllerAdd getInstance() {
        if (instance == null) {
            instance = new TmControllerAdd();
        }
        return instance;
    }

    /**
     * method for supporting adding tasks window
     *
     */
    public void add() {
        String option = new TmViewAdd().addDialog();
        if (!option.equals("-1")) {
            if (option.equals("1. Non-repeating task")) {
                new TmViewAddNonRepeatingTasks();
            } else {
                new TmViewAddRepeatingTasks();
            }
        }
    }

    /**
     * method for supporting non-repeating tasks adding window
     *
     * @param title  title of task
     * @param time start time of non-repeating task
     * @param active activity of task
     */
    public void addNonRepeatingTaskToList(String title, Date time, boolean active) {
        Task task = new Task(title, time);
        task.setActive(active);
        if (active && task.isActual()) {
            model.getAllTasksList().add(task);
            model.getActiveTasksList().add(task);
            model.sortActiveList(model.getActiveTasksList());
            model.notifyTask();
        } else {
            model.getAllTasksList().add(task);
        }
    }

    /**
     * method for supporting repeating tasks adding window
     *
     * @param title  title of task
     * @param start start time of repeating task
     * @param end end time of repeating task
     * @param interval period of time between repeating tasks
     * @param active activity of task
     */
    public void addRepeatingTaskToList(String title, Date start, Date end, int interval, boolean active) {
        Task task = new Task(title, start, end, interval);
        task.setActive(active);
        if (active && task.isActual()) {
            model.getAllTasksList().add(task);
            model.getActiveTasksList().add(task);
            model.sortActiveList(model.getActiveTasksList());
            model.notifyTask();
        } else {
            model.getAllTasksList().add(task);
        }
    }
}
