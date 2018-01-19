package com.sergey.savchenko.view;

import com.sergey.savchenko.controller.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class "TmViewAllTasksList", class for creating all tasks showing window
 *
 * Created by 2017 on 03.01.2018.
 */
public class TmViewAllTasksList {
    private TmControllerMain controllerMain;
    private TmControllerAdd controllerAdd;
    private TmControllerRemove controllerRemove;
    private TmControllerActivate controllerActivate;
    private TmControllerEdit controllerEdit;
    private JFrame frame;
    private JTextArea textArea;
    private JScrollPane jsPane;
    private JLabel label;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JButton active_inactiveTaskButton;
    private JButton editTaskButton;
    private JButton okButton;
    private JButton sortTaskButton;

    /**
     * constructor for creating object of TmViewAllTasksList class
     *
     */
    public TmViewAllTasksList() {
        controllerMain = TmControllerMain.getInstance();
        controllerAdd = TmControllerAdd.getInstance();
        controllerRemove = TmControllerRemove.getInstance();
        controllerActivate = TmControllerActivate.getInstance();
        controllerEdit = TmControllerEdit.getInstance();

        frame = new JFrame("TaskManager - AllTasks");
        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        label = new JLabel("List of ALL tasks:");
        label.getHorizontalTextPosition();
        label.setBounds(5, 0, 500, 26);

        textArea = new JTextArea();
        controllerMain.setAllJTextArea(textArea);
        controllerMain.updateAllTextArea();
        textArea.setEditable(false);

        jsPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsPane.setBounds(5, 25, 850, 400);

        addTaskButton = new JButton("add Task");
        addTaskButton.setBounds(frame.getWidth() - 141, 25, 120, 26);

        removeTaskButton = new JButton("remove Task");
        removeTaskButton.setBounds(frame.getWidth() - 141, 65, 120, 26);

        active_inactiveTaskButton = new JButton("active/inactive");
        active_inactiveTaskButton.setBounds(frame.getWidth() - 141, 105, 120, 26);

        editTaskButton = new JButton("edit Task");
        editTaskButton.setBounds(frame.getWidth() - 141, 145, 120, 26);

        okButton = new JButton("Ok");
        okButton.setBounds(frame.getWidth() - 141, 430, 120, 26);

        sortTaskButton = new JButton("sort Tasks by end date ");
        sortTaskButton.setBounds(5, 430, 210, 26);

        addTaskButton.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                controllerAdd.add();
                                            }
                                        }
        );

        removeTaskButton.addActionListener(new ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   controllerRemove.removeAllTask();
                                                   controllerMain.updateAllTextArea();
                                               }
                                           }
        );

        active_inactiveTaskButton.addActionListener(new ActionListener() {
                                                        public void actionPerformed(ActionEvent e) {
                                                            controllerActivate.inactivateAllTask();
                                                            controllerMain.updateAllTextArea();
                                                        }
                                                    }
        );

        editTaskButton.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 controllerEdit.editAllTask();
                                                 controllerMain.updateAllTextArea();
                                             }
                                         }
        );

        okButton.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent e) {
                                           controllerMain.updateActiveTextArea();
                                           frame.setVisible(false);
                                       }
                                   }
        );

        sortTaskButton.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 controllerMain.sortAllTasks();
                                                 controllerMain.updateAllTextArea();
                                             }
                                         }
        );

        frame.add(label);
        frame.add(jsPane);
        frame.add(addTaskButton);
        frame.add(removeTaskButton);
        frame.add(active_inactiveTaskButton);
        frame.add(editTaskButton);
        frame.add(okButton);
        frame.add(sortTaskButton);
        frame.setVisible(true);
    }
}
