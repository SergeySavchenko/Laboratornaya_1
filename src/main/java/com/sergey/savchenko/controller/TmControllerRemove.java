package com.sergey.savchenko.controller;

import com.sergey.savchenko.model.TmModel;
import com.sergey.savchenko.model.Task;
import com.sergey.savchenko.view.*;

/**
 * class "TmControllerRemove", class that connects view removing tasks classes with removing tasks methods and
 * other way round
 *
 * Created by 2017 on 17.01.2018.
 */
public class TmControllerRemove {
    private static TmControllerRemove instance;
    private TmControllerMain controllerMain;
    private TmModel model;

    /**
     * constructor for creating object of TmControllerRemove class
     *
     */
    public TmControllerRemove() {
        if (controllerMain == null) {
            controllerMain = TmControllerMain.getInstance();
            model = controllerMain.getModel();
        }
    }

    /**
     * method for getting instance of TmControllerRemove class
     *
     * @return instance of TmControllerRemove class
     */
    public static TmControllerRemove getInstance() {
        if (instance == null) {
            instance = new TmControllerRemove();
        }
        return instance;
    }

    /**
     * method for removing tasks from active and all tasks lists
     *
     */
    public void removeTask(Task task) {
        model.getActiveTasksList().remove(task);
        model.getAllTasksList().remove(task);
        model.notifyTask();
    }

    /**
     * method for supporting active tasks removing window
     *
     */
    public void removeActiveTask() {
        Task task;
        if (model.getActiveTasksList().size() > 0) {
            int position = new TmViewRemove().removeDialog(model.getActiveTasksList().size());
            if (position != -1) {
                task = model.getActiveTasksList().getTask(position - 1);
                model.getActiveTasksList().remove(task);
                model.getAllTasksList().remove(task);
                model.notifyTask();
            }
        } else {
            new TmViewRemove().emptyListDialog();
        }
    }

    /**
     * method for supporting all tasks removing window
     *
     */
    public void removeAllTask() {
        Task task;
        if (model.getAllTasksList().size() > 0) {
            int position = new TmViewRemove().removeDialog(model.getAllTasksList().size());
            if (position != -1) {
                task = model.getAllTasksList().getTask(position - 1);
                model.getActiveTasksList().remove(task);
                model.getAllTasksList().remove(task);
                model.notifyTask();
            }
        } else {
            new TmViewRemove().emptyListDialog();
        }
    }
}
