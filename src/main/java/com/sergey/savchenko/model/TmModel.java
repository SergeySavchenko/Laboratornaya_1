package com.sergey.savchenko.model;

import com.sergey.savchenko.controller.TmControllerMain;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * class "TmModel", defines methods for working with lists of tasks
 *
 * Created by 2017 on 03.01.2018.
 */
public class TmModel {
    private TaskList allTasksList;
    private TaskList activeTasksList;
    private TmControllerMain controllerMain;
    private ScreenNotification notify;

    /**
     * method for getting list of all existing tasks(allTasksList)
     *
     * @return list of all tasks
     */
    public TaskList getAllTasksList() {
        return allTasksList;
    }

    /**
     * method for getting list of active and actual tasks(activeTasksList)
     *
     * @return list of all tasks
     */
    public TaskList getActiveTasksList() {
        return activeTasksList;
    }

    /**
     * method for setting list of active and actual tasks(activeTasksList)
     *
     * @param list list of active and actual tasks
     */
    public void setActiveTasksList(TaskList list) {
        this.activeTasksList = list;
    }

    /**
     * method for setting instance of TmControllerMain class object
     *
     */
    public void setControllerMain() {
        controllerMain = TmControllerMain.getInstance();
    }


    /**
     * method for removing not actual tasks from active and actual list(activeTasksList)
     *
     */
    public void cleanNotActualTasks() {
        TaskList list = this.getActiveTasksList();
        for (int i = 0; i < list.size(); i++) {
            if (!list.getTask(i).isActual()) {
                list.remove(list.getTask(i));
            }
        }
    }

    /**
     * method for updating active and actual text area
     *
     */
    public void updateText() {
        controllerMain.updateActiveTextArea();
    }

    /**
     * method for putting all tasks from the file on disk to list of all tasks(allTasksList)
     *
     */
    public void initiateAllTasksList() throws IOException, ParseException {
        TaskList list = new ArrayTaskList();
        if (new File("Tasks.txt").exists()) {
            TaskIO.readText(list, new File("Tasks.txt"));
            allTasksList = list;
        } else {
            allTasksList = list;
            File file = new File("Tasks.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method for putting all active and actual tasks to the activeTasksList
     *
     */
    public void initiateActiveTasksList() {
        TaskList list = new ArrayTaskList();
        for (int i = 0; i < allTasksList.size(); i++) {
            if ((allTasksList.getTask(i) != null) && (allTasksList.getTask(i).isActive()) &&
                    (allTasksList.getTask(i).isActual())) {
                list.add(allTasksList.getTask(i));
            }
        }
        activeTasksList = list;
    }

    /**
     * method for sorting active and actual tasks by the date of completion
     *
     * @param list list of active and actual tasks(activeTasksList)
     */
    public void sortActiveList(TaskList list) {
        Date currentTime = new Date();
        Task task;
        ArrayTaskList taskList = (ArrayTaskList) list;
        if (taskList.size() > 1) {
            for (int i = taskList.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (taskList.getTask(i).nextTimeAfter(currentTime).before(taskList.getTask(j).nextTimeAfter(currentTime))) {
                        task = taskList.getTask(i);
                        taskList.set(i, taskList.getTask(j));
                        taskList.set(j, task);
                    }
                }
            }
        }
    }

    /**
     * method for sorting all existing tasks by the end point date
     *
     * @param list list of all tasks(allTasksList)
     */
    public void sortAllList(TaskList list) {
        Date tempDate1;
        Date tempDate2;
        Task task;
        ArrayTaskList taskList = (ArrayTaskList) list;
        if (taskList.size() > 1) {
            for (int i = taskList.size() - 1; i > 0; i--) {
                for (int j = 0; j < i; j++) {
                    if (taskList.getTask(j).isRepeated()) {
                        tempDate1 = taskList.getTask(j).getEndTime();
                    } else {
                        tempDate1 = taskList.getTask(j).getTime();
                    }
                    if (taskList.getTask(i).isRepeated()) {
                        tempDate2 = taskList.getTask(i).getEndTime();
                    } else {
                        tempDate2 = taskList.getTask(i).getTime();
                    }
                    if (tempDate2.after(tempDate1)) {
                        task = taskList.getTask(i);
                        taskList.set(i, taskList.getTask(j));
                        taskList.set(j, task);
                    }
                }
            }
        }
    }

    /**
     * method for canceling old task and setting new one for notifying
     *
     */
    public void notifyTask() {
        notify.getTimer().cancel();
        notify.setTimer(new Timer());
        notify.notifyTM();
    }

    /**
     * method for starting work with task notifying
     *
     */
    public void initiateNotification() {
        notify = new ScreenNotification(this);
    }

    /**
     * method for making text strings with specific information from active and actual list(activeTasksList)
     *
     * @param list list of active and actual tasks(activeTasksList)
     */
    public String printActiveList(TaskList list) {
        StringBuffer tasks = new StringBuffer();
        SimpleDateFormat sf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Date date = new Date();
        if (this.getActiveTasksList().size() == 0) {
            tasks.append("No active and actual tasks");
        } else {
            for (int i = 0; i < list.size(); i++) {
                tasks.append((i + 1) + ". " + list.getTask(i) + " NEXT TIME ON " + sf.format(list.getTask(i).nextTimeAfter(date)) + ";\n");
            }
            tasks.replace(tasks.length() - 2, tasks.length(), ".\n");
        }
        return tasks.toString();
    }

    /**
     * method for saving all tasks from list(allTasksList) to the file on disk
     *
     */
    public void writeToFile() throws IOException {
        TaskIO.writeText(this.getAllTasksList(), new File("Tasks.txt"));
    }
}
