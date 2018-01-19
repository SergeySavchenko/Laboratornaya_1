package com.sergey.savchenko.view;

import com.sergey.savchenko.controller.TmControllerAdd;
import com.sergey.savchenko.controller.TmControllerMain;
import com.sergey.savchenko.controller.TmControllerRemove;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.MAX_VALUE;

/**
 * class "TmViewSpinner", contains instances of controllers and methods for creating panels of date choosing
 *
 * Created by 2017 on 17.01.2018.
 */
public class TmViewSpinner {
    protected TmControllerMain controllerMain;
    protected static TmControllerAdd controllerAdd;
    protected static TmControllerRemove controllerRemove;
    private static Date date;

    /**
     * constructor for creating object of TmViewSpinner class
     *
     */
    public TmViewSpinner() {
        if (controllerMain == null) {
            controllerMain = TmControllerMain.getInstance();
        }
        if (controllerAdd == null) {
            controllerAdd = TmControllerAdd.getInstance();
        }
        if (controllerRemove == null) {
            controllerRemove = TmControllerRemove.getInstance();
        }
        date = new Date();
        date = TmViewSpinner.trimDate(date);
    }

    /**
     * method for creating panel of date choosing
     *
     * @return panel of date choosing
     */
    public JSpinner dateSpinner() {
        SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "yyyy-MM-dd HH:mm");
        spinner.setEditor(editor);
        return spinner;
    }

    /**
     * method for creating panel of interval choosing
     *
     * @return panel of interval choosing
     */
    public JSpinner intervalSpinner() {
        SpinnerModel sm = new SpinnerNumberModel(1, 1, MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(sm);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        editor.getTextField().setHorizontalAlignment(JTextField.LEFT);
        return spinner;
    }

    /**
     * method for setting to zero seconds and milliseconds of date
     *
     * @return cropped date
     */
    public static Date trimDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
