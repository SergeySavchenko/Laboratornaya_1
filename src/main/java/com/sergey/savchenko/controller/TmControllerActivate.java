package com.sergey.savchenko.controller;

import com.sergey.savchenko.model.TmModel;
import com.sergey.savchenko.model.Task;
import com.sergey.savchenko.view.*;

/**
 * class "TmControllerActivate", class that connects view activating tasks classes with activating tasks methods and
 * other way round
 *
 * Created by 2017 on 17.01.2018.
 */
public class TmControllerActivate {
    private static TmControllerActivate instance;
    private TmControllerMain controllerMain;
    private TmModel model;

    /**
     * constructor for creating object of TmControllerActivate class
     *
     */
    public TmControllerActivate() {
        if (controllerMain == null) {
            controllerMain = TmControllerMain.getInstance();
            model = controllerMain.getModel();
        }
    }

    /**
     * method for getting instance of TmControllerActivate class
     *
     * @return instance of TmControllerActivate class
     */
    public static TmControllerActivate getInstance() {
        if (instance == null) {
            instance = new TmControllerActivate();
        }
        return instance;
    }

    /**
     * method for supporting active tasks inactivating window
     *
     */
    public void inactivateActiveTask() {
        Task task;
        if (model.getActiveTasksList().size() > 0) {
            int position = new TmViewActivate().inactivateDialog(model.getActiveTasksList().size());
            if (position != -1) {
                task = model.getActiveTasksList().getTask(position - 1);
                model.getActiveTasksList().remove(task);
                task.setActive(false);
                model.sortActiveList(model.getActiveTasksList());
                model.notifyTask();
            }
        } else {
            new TmViewActivate().emptyListDialog();
        }
    }

    /**
     * method for supporting all tasks activating/inactivating window
     *
     */
    public void inactivateAllTask() {
        Task task;
        int result = 0;
        if (model.getAllTasksList().size() > 0) {
            int position = new TmViewActivate().inactivateDialog(model.getAllTasksList().size());
            if (position != -1) {
                task = model.getAllTasksList().getTask(position - 1);
                if (task.isActive()) {
                    model.getActiveTasksList().remove(task);
                    task.setActive(false);
                    model.sortActiveList(model.getActiveTasksList());
                    model.notifyTask();

                } else if (!(task.isActive())) {
                    task.setActive(true);
                    for (int i = 0; i < model.getActiveTasksList().size(); i++) {
                        if (task.equals(model.getActiveTasksList().getTask(i))) {
                            result = 1;
                        }
                    }
                    if (task.isActual()) {
                        if (result == 0) {
                            model.getActiveTasksList().add(task);
                            model.sortActiveList(model.getActiveTasksList());
                            model.notifyTask();
                        }
                    }
                }
            }
        } else {
            new TmViewActivate().emptyListDialog();
        }
    }
}
