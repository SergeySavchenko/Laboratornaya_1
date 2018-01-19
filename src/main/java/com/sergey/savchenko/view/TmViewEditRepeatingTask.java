package com.sergey.savchenko.view;

import com.sergey.savchenko.model.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * class "TmViewEditRepeatingTask", class for creating editing window for repeating task
 *
 * Created by 2017 on 16.01.2018.
 */
public class TmViewEditRepeatingTask extends TmViewSpinner {
    private JFrame frame;
    private JTextField titleTextField;
    private JLabel titleLabel;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JLabel intervalLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox activeCheckbox;
    private JCheckBox isNonRepeatingCheckbox;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JSpinner intervalSpinner;

    /**
     * constructor for creating object of TmViewEditRepeatingTask class
     *
     * @param task editing task
     */
    public TmViewEditRepeatingTask(Task task) {
        frame = new JFrame("Edit repeating task");
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        titleLabel = new JLabel("Enter the task title:");
        titleLabel.setBounds(10, -5, 250, 26);

        titleTextField = new JTextField();
        titleTextField.setText(task.getTitle());
        titleTextField.setBounds(10, 15, 250, 26);

        startTimeLabel = new JLabel("Enter start time of the task:");
        startTimeLabel.setBounds(10, 40, 250, 26);

        startTimeSpinner = this.dateSpinner();
        startTimeSpinner.setValue(task.getStartTime());
        startTimeSpinner.setBounds(10, 60, 250, 26);

        endTimeLabel = new JLabel("Enter end time of the task:");
        endTimeLabel.setBounds(10, 90, 250, 26);

        endTimeSpinner = this.dateSpinner();
        endTimeSpinner.setValue(task.getEndTime());
        endTimeSpinner.setBounds(10, 110, 250, 26);

        intervalLabel = new JLabel("Enter interval between tasks(minutes):");
        intervalLabel.setBounds(10, 140, 250, 26);

        intervalSpinner = this.intervalSpinner();
        intervalSpinner.setValue(task.getRepeatInterval() / 60);
        intervalSpinner.setBounds(10, 160, 250, 26);

        activeCheckbox = new JCheckBox("Active/Inactive task");
        if (task.isActive()) {
            activeCheckbox.setSelected(true);
        } else {
            activeCheckbox.setSelected(false);
        }
        activeCheckbox.setBounds(7, 183, 500, 26);

        isNonRepeatingCheckbox = new JCheckBox("is Non-repeating Task");
        isNonRepeatingCheckbox.setSelected(false);
        isNonRepeatingCheckbox.setBounds(7, 203, 500, 26);

        okButton = new JButton("Ok");
        okButton.setBounds(30, frame.getHeight() - 70, 100, 26);
        okButton.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent e) {
                                           String title = titleTextField.getText();
                                           Date start = (Date) startTimeSpinner.getValue();
                                           Date end = (Date) endTimeSpinner.getValue();
                                           Integer interval = (Integer) intervalSpinner.getValue();
                                           interval = interval * 60;
                                           boolean active = activeCheckbox.isSelected();
                                           if (isNonRepeatingCheckbox.isSelected()) {
                                               task.setTitle(title);
                                               task.setTime(end);
                                               task.setActive(active);
                                               frame.setVisible(false);
                                               new TmViewEditNonRepeatingTask(task);
                                           } else {
                                               if ((title.length() == 0) && !(end.after(start))) {
                                                   JOptionPane.showMessageDialog(null,
                                                           "Title of the task is EMPTY!\nEnd time before or equals start time!");
                                               } else if (title.length() == 0) {
                                                   JOptionPane.showMessageDialog(null, "Title of the task is EMPTY!");
                                               } else if (!(end.after(start))) {
                                                   JOptionPane.showMessageDialog(null, "End time before or equals start time!");
                                               } else {
                                                   controllerRemove.removeTask(task);
                                                   controllerAdd.addRepeatingTaskToList(title, start, end, interval, active);
                                                   controllerMain.updateActiveTextArea();
                                                   if (controllerMain.getAllTextArea() != null) {
                                                       controllerMain.updateAllTextArea();
                                                   }
                                                   frame.setVisible(false);
                                               }
                                           }

                                       }
                                   }
        );

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, frame.getHeight() - 70, 100, 26);
        cancelButton.addActionListener(new ActionListener() {
                                           public void actionPerformed(ActionEvent e) {
                                               frame.setVisible(false);
                                               controllerMain.updateActiveTextArea();
                                               if (controllerMain.getAllTextArea() != null) {
                                                   controllerMain.updateAllTextArea();
                                               }
                                           }
                                       }
        );
        frame.add(titleLabel);
        frame.add(titleTextField);
        frame.add(startTimeLabel);
        frame.add(startTimeSpinner);
        frame.add(endTimeLabel);
        frame.add(endTimeSpinner);
        frame.add(intervalLabel);
        frame.add(intervalSpinner);
        frame.add(activeCheckbox);
        frame.add(isNonRepeatingCheckbox);
        frame.add(okButton);
        frame.add(cancelButton);
        frame.setVisible(true);
    }
}
