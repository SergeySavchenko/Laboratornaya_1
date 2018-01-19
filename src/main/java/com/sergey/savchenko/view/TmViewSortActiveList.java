package com.sergey.savchenko.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * class "TmViewSortActiveList", class for creating sort window for active and actual tasks
 *
 * Created by 2017 on 16.01.2018.
 */
public class TmViewSortActiveList extends TmViewSpinner {
    private JFrame frame;
    private JLabel startTimeLabel;
    private JLabel endTimeLabel;
    private JButton okButton;
    private JButton cancelButton;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;

    /**
     * constructor for creating object of TmViewSortActiveList class
     *
     */
    public TmViewSortActiveList() {
        frame = new JFrame("Sort active list");
        frame.setSize(300, 165);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        startTimeLabel = new JLabel("Enter start point of time interval:");
        startTimeLabel.setBounds(10, -5, 250, 26);

        startTimeSpinner = this.dateSpinner();
        startTimeSpinner.setBounds(10, 15, 250, 26);

        endTimeLabel = new JLabel("Enter end point of time interval:");
        endTimeLabel.setBounds(10, 40, 250, 26);

        endTimeSpinner = this.dateSpinner();
        endTimeSpinner.setBounds(10, 60, 250, 26);

        okButton = new JButton("Ok");
        okButton.setBounds(30, frame.getHeight() - 70, 100, 26);
        okButton.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent e) {
                                           Date start = (Date) startTimeSpinner.getValue();
                                           Date end = (Date) endTimeSpinner.getValue();
                                           if (!end.after(start)) {
                                               JOptionPane.showMessageDialog(null,
                                                       "End time before or equals start time!");
                                           } else {
                                               try {
                                                   controllerMain.sortActiveTasks(start, end);
                                               } catch (IllegalAccessException e1) {
                                                   e1.printStackTrace();
                                               } catch (InstantiationException e1) {
                                                   e1.printStackTrace();
                                               }
                                               controllerMain.updateActiveTextArea();
                                               frame.setVisible(false);
                                           }
                                       }
                                   }
        );

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, frame.getHeight() - 70, 100, 26);
        cancelButton.addActionListener(new ActionListener() {
                                           public void actionPerformed(ActionEvent e) {
                                               frame.setVisible(false);
                                           }
                                       }
        );

        frame.add(startTimeLabel);
        frame.add(startTimeSpinner);
        frame.add(endTimeLabel);
        frame.add(endTimeSpinner);
        frame.add(okButton);
        frame.add(cancelButton);
        frame.setVisible(true);
    }
}
