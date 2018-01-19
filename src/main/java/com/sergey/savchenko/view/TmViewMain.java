package com.sergey.savchenko.view;

import com.sergey.savchenko.controller.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.text.ParseException;

/**
 * class "TmViewMain", class for creating active and actual tasks showing window
 * <p>
 * Created by SergeySavchenko on 26.12.2017.
 */

public class TmViewMain extends JFrame {
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
    private JButton inactivateTaskButton;
    private JButton editTaskButton;
    private JButton allTasksListButton;
    private JButton sortTaskButton;
    private JButton allActiveAndActualTasksButton;

    /**
     * constructor for creating object of TmViewMain class
     */
    public TmViewMain() throws IOException, ParseException {
        controllerMain = TmControllerMain.getInstance();
        controllerMain.getModel().setControllerMain();
        controllerAdd = TmControllerAdd.getInstance();
        controllerRemove = TmControllerRemove.getInstance();
        controllerActivate = TmControllerActivate.getInstance();
        controllerEdit = TmControllerEdit.getInstance();
        controllerMain.initiate();

        frame = new JFrame("TaskManager - Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    controllerMain.saveAllTasksListToFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }
        });

        frame.setSize(1000, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        label = new JLabel("List of ACTIVE and ACTUAL sorted tasks:");
        label.getHorizontalTextPosition();
        label.setBounds(5, 0, 500, 26);

        textArea = new JTextArea();
        controllerMain.setActiveJTextArea(textArea);
        controllerMain.updateActiveTextArea();
        textArea.setEditable(false);

        jsPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsPane.setBounds(5, 25, 850, 400);

        addTaskButton = new JButton("add Task");
        addTaskButton.setBounds(frame.getWidth() - 141, 25, 120, 26);

        removeTaskButton = new JButton("remove Task");
        removeTaskButton.setBounds(frame.getWidth() - 141, 65, 120, 26);

        inactivateTaskButton = new JButton("inactivate Task");
        inactivateTaskButton.setBounds(frame.getWidth() - 141, 105, 120, 26);

        editTaskButton = new JButton("edit Task");
        editTaskButton.setBounds(frame.getWidth() - 141, 145, 120, 26);

        allTasksListButton = new JButton("All TASKS");
        allTasksListButton.setBounds(frame.getWidth() - 141, 430, 120, 26);

        sortTaskButton = new JButton("sort Task");
        sortTaskButton.setBounds(5, 430, 210, 26);

        allActiveAndActualTasksButton = new JButton("all ACTIVE and ACTUAL Tasks");
        allActiveAndActualTasksButton.setBounds(225, 430, 210, 26);

        addTaskButton.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                                controllerAdd.add();
                                            }
                                        }
        );

        removeTaskButton.addActionListener(new ActionListener() {
                                               public void actionPerformed(ActionEvent e) {
                                                   controllerRemove.removeActiveTask();
                                                   controllerMain.updateActiveTextArea();
                                               }
                                           }
        );

        inactivateTaskButton.addActionListener(new ActionListener() {
                                                   public void actionPerformed(ActionEvent e) {
                                                       controllerActivate.inactivateActiveTask();
                                                       controllerMain.updateActiveTextArea();
                                                   }
                                               }
        );

        editTaskButton.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 controllerEdit.editActiveTask();
                                                 controllerMain.updateActiveTextArea();
                                             }
                                         }
        );

        allTasksListButton.addActionListener(new ActionListener() {
                                                 public void actionPerformed(ActionEvent e) {
                                                     new TmViewAllTasksList();
                                                 }
                                             }
        );

        sortTaskButton.addActionListener(new ActionListener() {
                                             public void actionPerformed(ActionEvent e) {
                                                 new TmViewSortActiveList();
                                             }
                                         }
        );

        allActiveAndActualTasksButton.addActionListener(new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                controllerMain.getAllActiveTasks();
                                                                controllerMain.updateActiveTextArea();
                                                            }
                                                        }
        );

        frame.add(label);
        frame.add(jsPane);
        frame.add(addTaskButton);
        frame.add(removeTaskButton);
        frame.add(inactivateTaskButton);
        frame.add(editTaskButton);
        frame.add(allTasksListButton);
        frame.add(sortTaskButton);
        frame.add(allActiveAndActualTasksButton);
        frame.setVisible(true);
    }
}
