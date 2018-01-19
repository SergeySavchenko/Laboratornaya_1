package com.sergey.savchenko.model;

import javax.swing.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * class "ScreenNotification", notifies about time of task execution
 *
 * Created by 2017 on 03.01.2018.
 */
public class ScreenNotification {
    private TmModel model;
    private String title;
    private Timer timer;

    /**
     * constructor for creating notification object
     *
     * @param model reference to object of TmModel class
     */
    public ScreenNotification(TmModel model) {
        timer = new Timer();
        this.model = model;
        this.notifyTM();
    }

    /**
     * method for getting timer of task
     *
     * @return timer of task
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * method for setting timer of task
     *
     * @param timer timer of task
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * method for putting task to notify process
     *
     */
    public void notifyTM() {
        if (model.getActiveTasksList().getTask(0) != null) {
            title = model.getActiveTasksList().getTask(0).getTitle();
            Date executionTime = model.getActiveTasksList().getTask(0).nextTimeAfter(new Date());
            timer.schedule(new TimerTask() {
                public void run() {

                    JOptionPane.showMessageDialog(null, "It's time to do the task: \"" + title + "\"");
                    model.cleanNotActualTasks();
                    model.sortActiveList(model.getActiveTasksList());
                    model.updateText();
                    notifyTM();
                }
            }, executionTime);
        }
    }
}
