package com.sergey.savchenko.view;

import javax.swing.*;

/**
 * class "TmViewEdit", contains methods for editing window
 *
 * Created by 2017 on 15.01.2018.
 */
public class TmViewEdit {

    /**
     * method for editing window showing
     *
     * @param numberOfTasks number of existing tasks
     * @return number of task to edit
     */
    public int editDialog(int numberOfTasks) {
        Object[] numbers = new Object[numberOfTasks];
        for (int i = 0; i < numberOfTasks; i++) {
            numbers[i] = i + 1;
        }
        JLabel label = new JLabel("Select the task number to edit: ");
        Integer position = (Integer) JOptionPane.showInputDialog(
                null,
                label,
                "Edit",
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
     * method for showing editing emptyListDialog
     *
     */
    public void emptyListDialog() {
        JOptionPane.showMessageDialog(null, "          No tasks to edit");
    }
}
