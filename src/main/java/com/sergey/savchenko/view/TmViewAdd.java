package com.sergey.savchenko.view;

import javax.swing.*;

/**
 * class "TmViewAdd", contains method for adding window
 *
 * Created by 2017 on 03.01.2018.
 */
public class TmViewAdd {
    /**
     * method for adding window showing
     *
     * @return one of the text strings from array
     */
    public String addDialog() {
        Object[] numbers = {"1. Non-repeating task", "2.  Repeating task"};
        JLabel label = new JLabel("Select variant of task to add: ");
        String option = (String) JOptionPane.showInputDialog(
                null,
                label,
                "Add",
                JOptionPane.PLAIN_MESSAGE,
                null,
                numbers,
                "1. Non-repeating task");
        if (option != null) {
            return option;
        } else {
            return "-1";
        }
    }
}
