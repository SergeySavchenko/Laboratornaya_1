package com.sergey.savchenko.view;

import javax.swing.*;

/**
 * class "TmViewRemove", contains methods for removing window
 *
 * Created by 2017 on 03.01.2018.
 */
public class TmViewRemove {

    /**
     * method for removing window showing
     *
     * @param numberOfTasks number of existing tasks
     * @return number of task to delete
     */
    public int removeDialog(int numberOfTasks) {
        Object[] numbers = new Object[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            numbers[i] = i + 1;
        }
        JLabel label = new JLabel("Select the task number to delete: ");
        Integer position = (Integer) JOptionPane.showInputDialog(
                null,
                label,
                "Remove",
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
     * method for showing removing emptyListDialog
     *
     */
    public void emptyListDialog() {
        JOptionPane.showMessageDialog(null, "        No tasks to delete");
    }
}
