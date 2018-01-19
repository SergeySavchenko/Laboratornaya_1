package com.sergey.savchenko.controller;

import com.sergey.savchenko.model.TaskList;
import com.sergey.savchenko.model.TmModel;
import com.sergey.savchenko.model.Tasks;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * class "TmControllerMain", main class that connects view classes with model classes and other way round
 *
 * Created by SergeySavchenko on 26.12.2017.
 */
public class TmControllerMain {
    private static TmControllerMain instance;
    private TmModel model;
    private JTextArea activeTextArea;
    private JTextArea allTextArea;

    /**
     * constructor for creating object of TmControllerMain class
     *
     */
    public TmControllerMain() {
        model = new TmModel();
    }

    /**
     * method for getting instance of TmControllerMain class
     *
     */
    public static TmControllerMain getInstance() {
        if (instance == null) {
            instance = new TmControllerMain();
        }
        return instance;
    }

    /**
     * method for setting instance of active and actual tasks text area
     *
     * @param activeTextArea active tasks text area
     */
    public void setActiveJTextArea(JTextArea activeTextArea) {
        this.activeTextArea = activeTextArea;
    }

    /**
     * method for setting instance of all tasks text area
     *
     * @param allTextArea all tasks text area
     */
    public void setAllJTextArea(JTextArea allTextArea) {
        this.allTextArea = allTextArea;
    }

    /**
     * method for getting instance of all tasks text area
     *
     * @return instance of all tasks text area
     */
    public JTextArea getAllTextArea() {
        return allTextArea;
    }

    /**
     * method for getting instance of TmModel class object
     *
     * @return instance of TmModel class object
     */
    public TmModel getModel() {
        return model;
    }

    /**
     * method for preparation allTasksList and activeTasksList to work
     *
     */
    public void initiate() throws IOException, ParseException {
        model.initiateAllTasksList();
        model.initiateActiveTasksList();
        model.sortActiveList(model.getActiveTasksList());
        model.initiateNotification();
    }

    /**
     * method for putting all active and actual tasks from allTasksList to activeTasksList
     *
     */
    public void getAllActiveTasks() {
        model.initiateActiveTasksList();
        model.sortActiveList(model.getActiveTasksList());
        model.notifyTask();
    }

    /**
     * method for interpreting active and actual tasks in text string
     *
     * @return text string of active and actual tasks
     */
    public String printActiveTasks() {
        return model.printActiveList(model.getActiveTasksList());
    }

    /**
     * method for updating active and actual tasks text area
     *
     */
    public void updateActiveTextArea() {
        String textArea = this.printActiveTasks();
        if (textArea.length() != 0) {
            activeTextArea.setText(textArea);
        } else {
            activeTextArea.setText("No active and actual tasks");
        }
    }

    /**
     * method for interpreting all tasks in text string
     *
     * @return text string of all tasks
     */
    public String printAllTasks() {
        return model.getAllTasksList().toString();
    }

    /**
     * method for updating all tasks text area
     *
     */
    public void updateAllTextArea() {
        String textArea = this.printAllTasks();
        if (textArea.length() != 0) {
            allTextArea.setText(textArea);
        } else {
            allTextArea.setText("No any task");
        }
    }

    /**
     * method for saving all tasks from list(allTasksList) to the file on disk
     *
     */
    public void saveAllTasksListToFile() throws IOException {
        model.writeToFile();
    }

    /**
     * method for getting new list of active and actual tasks which corresponds to a given time interval
     *
     * @param start start point of time interval
     * @param  end end point of time interval
     */
    public void sortActiveTasks(Date start, Date end) throws IllegalAccessException, InstantiationException {
        TaskList list = (TaskList) Tasks.incoming(model.getActiveTasksList(), start, end);
        model.setActiveTasksList(list);
        model.notifyTask();
    }

    /**
     * method for sorting list of all tasks by end point time
     *
     */
    public void sortAllTasks() {
        model.sortAllList(model.getAllTasksList());
    }
}
