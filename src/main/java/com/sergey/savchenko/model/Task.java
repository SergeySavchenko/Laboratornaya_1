package com.sergey.savchenko.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class "Task", describes the task
 *
 * @author SergeySavchenko
 */
public class Task implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    public static final int MILLI_SECONDS = 1000;
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    /**
     * constructor for creating non-repeating tasks
     *
     * @param title name of task
     * @param time  start time of non-repeating task
     */
    public Task(String title, Date time) {
        this.title = title;
        this.time = time;
        start = new Date(0);
        end = new Date(0);
        interval = 0;
        active = false;
    }

    /**
     * constructor for creating repeating tasks
     *
     * @param title    name of task
     * @param start    start time of repeating task
     * @param end      end time of repeating task
     * @param interval period of time between repeating tasks
     */
    public Task(String title, Date start, Date end, int interval) throws IllegalArgumentException {
        String message = "Time or time interval can't be negative or equal to zero";
        if (!end.after(start) || (interval <= 0))
            throw new IllegalArgumentException(message);
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval * MILLI_SECONDS;
        time = new Date(0);
        active = false;
    }

    /**
     * method for reading name of task
     *
     * @return name of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * method for setting name of task
     *
     * @param title name of task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * method for getting activity of task
     *
     * @return activity of task
     */
    public boolean isActive() {
        return active;
    }

    /**
     * method for getting actuality of task
     *
     * @return actuality of task
     */
    public boolean isActual() {
        return this.nextTimeAfter(new Date()) != null;
    }

    /**
     * method for setting activity of task
     *
     * @param active activity of task
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * method for getting start time of task
     *
     * @return start time of task
     */
    public Date getTime() {
        return time;
    }

    /**
     * method for setting repetitiveness of task
     * (from repeating to non-repeating)
     *
     * @param time time of task
     */
    public void setTime(Date time) {
        this.time = time;
        start = time;
        end = time;
        interval = 0;
        active = false;
    }

    /**
     * method for getting start time of task
     *
     * @return start time of task
     */
    public Date getStartTime() {
        return start;
    }

    /**
     * method for getting end time of task
     *
     * @return end time of task
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * method for getting interval between tasks
     *
     * @return period of time between tasks
     */
    public int getRepeatInterval() {
        return interval / MILLI_SECONDS;
    }

    /**
     * method for changing repetitiveness of task
     * (from non-repeating to repeating)
     *
     * @param start    start time of task
     * @param end      end time of task
     * @param interval period of time between repeating tasks
     */
    public void setTime(Date start, Date end, int interval) throws IllegalArgumentException {
        String message 
       = "End time can't be less than start time or interval can't be negative";
        if ((!end.after(start)) || (interval <= 0)) 
                                    throw new IllegalArgumentException(message);
        this.start = start;
        this.end = end;
        this.interval = interval * MILLI_SECONDS;
        time = start;
        active = false;
    }

    /**
     * method for getting repetitiveness of task
     *
     * @return task repeats or not
     */
    public boolean isRepeated() {
        return interval != 0;
    }

    /**
     * method for gettiung time of next task
     *
     * @param current set current time
     * @return time of next task
     */
    public Date nextTimeAfter(Date current) {
        Date nextTime = start;
        if (isActive()) {
            if (isRepeated()) {
                if (start.after(current)) { return start;
                } else if (!end.before(current)) {
                    while (!nextTime.after(current)) {
                        nextTime = new Date(nextTime.getTime() + interval);
                    }
                    if (!end.before(nextTime)) {
                        return nextTime;
                    } else return null; 
                    } else return null;
            } else if (time.after(current)) { return time; 
            } else return null;
        } else return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        if (isRepeated() && task.isRepeated()) {
            if (active != task.active) return false;
            if (!start.equals(task.start)) return false;
            if (!end.equals(task.end)) return false;
            if (interval != task.interval) return false;
        } else {
            if (active != task.active) return false;
            if (!time.equals(task.time)) return false;
        }
        return title.equals(task.title);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = title.hashCode();
        if (isRepeated()) {
            result = prime * result + start.hashCode();
            result = prime * result + end.hashCode();
            result = prime * result + interval;
            result = prime * result + (active ? 1 : 0);
        } else {
            result = prime * result + time.hashCode();
            result = prime * result + (active ? 1 : 0);
        }
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat sf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        String activity = "";
        if (!active) {
            activity = " inactive";
        }
        if (!isRepeated()) {
            return "\"" + title + "\" at " + sf.format(time) + activity;
        } else {
            return "\"" + title + "\" from " + sf.format(start) + " to "
              + sf.format(end) + " every " + writeInterval(interval) + activity;
        }
    }

    /**
     * method for representing the interval in a given form
     *
     * @param interval time between tasks in milliseconds
     * @return task in string presentation
     */
    private String writeInterval(int interval) {
        int intervalInSeconds = interval / MILLI_SECONDS;
        int day;
        int hour;
        int minute;
        int second;
        String strResult;
        String strDay;
        String strHour;
        String strMinute = "";
        String strSecond = "";
        day = intervalInSeconds / 86400;
        hour = (intervalInSeconds % 86400) / 3600;
        minute = (intervalInSeconds % 3600) / 60;
        second = intervalInSeconds % 60;
        if(day > 0) {
            if (day > 1) {
                strDay = day + " days, ";
            } else {
                strDay = day + " day, ";
            }
        } else {
            strDay = "";
        }

        if(hour > 0) {
            if (hour > 1) {
                strHour = hour + " hours, ";
            } else {
                strHour = hour + " hour, ";
            }
        } else {
            strHour = "";
        }

        if(minute > 0) {
            if (minute > 1) {
                strMinute = minute + " minutes, ";
            } else {
                strMinute = minute + " minute, ";
            }
        } else {
            strMinute = "";
        }

        if(second > 0) {
            if (second > 1) {
                strSecond = second + " seconds";
            } else {
                strSecond = second + " second";
            }
        } else {
            strSecond = "";
        }
        strResult = strDay + strHour + strMinute + strSecond;
        String s = strResult.substring(strResult.length()- 2, strResult.length());
        if (s.equals(", ")) {
            strResult = strResult.substring(0, strResult.length() - 2);
            strResult = "[" + strResult + "]";
        } else {
            strResult = "[" + strResult + "]";
        }
        return strResult;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}