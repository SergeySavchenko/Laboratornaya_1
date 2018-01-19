package com.sergey.savchenko.view;

import javax.swing.*;

/**
 * class "TmViewActivate", contains methods for activating window
 *
 * Created by 2017 on 03.01.2018.
 */
public class TmViewActivate {
    /**
     * method for activating window showing
     *
     * @param numberOfTasks number of existing tasks
     * @return number of task to activate
     */
    public int inactivateDialog(int numberOfTasks) {
        Object[] numbers = new Object[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            numbers[i] = i + 1;
        }
        JLabel label = new JLabel("Select the task number to inactivate: ");
        Integer position = (Integer) JOptionPane.showInputDialog(
                null,
                label,
                "Inactivate",
                JOptionPane.PLAIN_MESSAGE,
                null,
                numbers,
                1);
        if (position != null) {
            return position;
        } else {
            return -1;
        }
    }

    /**
     * method for showing activating emptyListDialog
     *
     */
    public void emptyListDialog() {
        JOptionPane.showMessageDialog(null, "      No tasks to inactivate");
    }
}
