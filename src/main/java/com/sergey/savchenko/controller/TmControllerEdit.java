package com.sergey.savchenko.controller;

import com.sergey.savchenko.model.TmModel;
import com.sergey.savchenko.model.Task;
import com.sergey.savchenko.view.*;

/**
 * class "TmControllerEdit", class that connects view editing tasks classes with editing tasks methods and
 * other way round
 * Created by 2017 on 17.01.2018.
 */
public class TmControllerEdit {
    private static TmControllerEdit instance;
    private TmControllerMain controllerMain;
    private TmModel model;


    /**
     * constructor for creating object of TmControllerEdit class
     *
     */
    public TmControllerEdit() {
        if (controllerMain == null) {
            controllerMain = TmControllerMain.getInstance();
            model = controllerMain.getModel();
        }
    }

    /**
     * method for getting instance of TmControllerEdit class
     *
     * @return instance of TmControllerEdit class
     */
    public static TmControllerEdit getInstance() {
        if (instance == null) {
            instance = new TmControllerEdit();
        }
        return instance;
    }

    /**
     * method for supporting active tasks editing window
     *
     */
    public void editActiveTask() {
        Task task;
        if (model.getActiveTasksList().size() > 0) {
            int position = new TmViewEdit().editDialog(model.getActiveTasksList().size());
            if (position != -1) {
                task = model.getActiveTasksList().getTask(position - 1);
                if (task.getRepeatInterval() == 0) {
                    new TmViewEditNonRepeatingTask(task);
                } else {
                    new TmViewEditRepeatingTask(task);
                }
            }
        } else {
            new TmViewEdit().emptyListDialog();
        }
    }

    /**
     * method for supporting all tasks editing window
     *
     */
    public void editAllTask() {
        Task task;
        if (model.getAllTasksList().size() > 0) {
            int position = new TmViewEdit().editDialog(model.getAllTasksList().size());
            if (position != -1) {
                task = model.getAllTasksList().getTask(position - 1);
                if (task.getRepeatInterval() == 0) {
                    new TmViewEditNonRepeatingTask(task);
                } else {
                    new TmViewEditRepeatingTask(task);
                }
            }
        } else {
            new TmViewEdit().emptyListDialog();
        }
    }
}
