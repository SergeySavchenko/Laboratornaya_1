package com.sergey.savchenko.view;

import com.sergey.savchenko.model.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * class "TmViewEditNonRepeatingTask", class for creating editing window for non-repeating task
 * <p>
 * Created by 2017 on 16.01.2018.
 */
public class TmViewEditNonRepeatingTask extends TmViewSpinner {
    private JFrame frame;
    private JTextField titleTextField;
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox activeCheckbox;
    private JCheckBox isRepeatingCheckbox;
    private JSpinner timeSpinner;

    /**
     * constructor for creating object of TmViewEditNonRepeatingTask class
     *
     * @param task editing task
     */
    public TmViewEditNonRepeatingTask(Task task) {
        frame = new JFrame("Edit non-repeating task");
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        titleLabel = new JLabel("Enter the task title");
        titleLabel.setBounds(10, 0, 250, 26);

        titleTextField = new JTextField();
        titleTextField.setText(task.getTitle());
        titleTextField.setBounds(10, 25, 250, 26);

        dateLabel = new JLabel("Enter the time of the task");
        dateLabel.setBounds(10, 90, 250, 26);

        timeSpinner = dateSpinner();
        timeSpinner.setValue(task.getTime());
        timeSpinner.setBounds(10, 115, 250, 26);

        activeCheckbox = new JCheckBox("Active/Inactive task");
        if (task.isActive()) {
            activeCheckbox.setSelected(true);
        } else {
            activeCheckbox.setSelected(false);
        }
        activeCheckbox.setBounds(10, 150, 500, 26);

        isRepeatingCheckbox = new JCheckBox("is Repeating Task");
        isRepeatingCheckbox.setSelected(false);
        isRepeatingCheckbox.setBounds(10, 180, 500, 26);

        okButton = new JButton("Ok");
        okButton.setBounds(30, frame.getHeight() - 70, 100, 26);
        okButton.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent e) {
                                           String title = titleTextField.getText();
                                           Date time = (Date) timeSpinner.getValue();
                                           boolean active = activeCheckbox.isSelected();
                                           if (isRepeatingCheckbox.isSelected()) {
                                               task.setTitle(title);
                                               task.setTime(time, new Date(time.getTime() + 1000 * 60), 60);
                                               task.setActive(active);
                                               frame.setVisible(false);
                                               new TmViewEditRepeatingTask(task);
                                           } else {
                                               if (title.length() == 0) {
                                                   JOptionPane.showMessageDialog(null, "Title of the task is EMPTY!");
                                               } else {
                                                   controllerRemove.removeTask(task);
                                                   controllerAdd.addNonRepeatingTaskToList(title, time, active);
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
        frame.add(dateLabel);
        frame.add(timeSpinner);
        frame.add(activeCheckbox);
        frame.add(isRepeatingCheckbox);
        frame.add(okButton);
        frame.add(cancelButton);
        frame.setVisible(true);
    }
}
