package demo.tasks;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by wdalo on 29-9-2015.
 */
class Main {
    private JPanel mainPanel;
    private JButton openButton;
    private JButton saveButton;
    private JButton addTaskButton;
    private JButton removeTaskButton;
    private JTable tableTasks;
    private JButton editTaskButton;
    private JButton showGraphButton;
    private JButton newButton;
    private JButton saveAsButton;
    private JLabel tasksLabel;
    private final TaskTableModel taskTableModel;
    private final Main main;
    private File file;
    private final Tasks tasks;

    private Main() {

        tasks = new Tasks();
        main = this;

        taskTableModel = new TaskTableModel(tasks.getTasks());
        tableTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableTasks.setModel(taskTableModel);

        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddEditTask addEditTask = new AddEditTask(tasks.getTasks(), main, null);
                addEditTask.setTitle("Add Task");
                addEditTask.setSize(480, 340);
                addEditTask.setResizable(false);
                addEditTask.setVisible(true);
            }
        });
        removeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (tableTasks.getSelectedRow() > -1) {
                        taskTableModel.removeTask(tableTasks.getSelectedRow());
                        UpdateTable();

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a task you want to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    ShowEmptyError();
                }
            }
        });
        showGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    Graph graph = new Graph(tasks.getTasks());
                    graph.setTitle("Graph");
                    graph.setSize(480, 340);
                    graph.setResizable(false);
                    graph.setVisible(true);
                } else {
                    ShowEmptyError();
                }
            }
        });
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (tableTasks.getSelectedRow() > -1) {
                        AddEditTask addEditTask = new AddEditTask(tasks.getTasks(), main, tableTasks.getSelectedRow());
                        addEditTask.setTitle("Edit Task");
                        addEditTask.setSize(480, 340);
                        addEditTask.setResizable(false);
                        addEditTask.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Please select a task you want to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    ShowEmptyError();
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    if (file != null) {
                        tasks.createXML(String.valueOf(file));
                        tasksLabel.setText("Tasks (" + file + "):");
                    } else {
                        SaveAs();
                    }

                } else {
                    ShowEmptyError();
                }
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Show dialog and open the file.
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Open tasks");

                FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("XML Files", "XML");
                jFileChooser.setFileFilter(fileNameExtensionFilter);

                int userSelection = jFileChooser.showSaveDialog(mainPanel);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    if (jFileChooser.getSelectedFile() != null) {
                        tasks.readXML(jFileChooser.getSelectedFile());

                        file = jFileChooser.getSelectedFile();
                        tasksLabel.setText("Tasks (" + file + "):");

                        UpdateTable();
                    }
                }
            }
        });
        saveAsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tasks.getTasks().size() > 0) {
                    SaveAs();
                } else {
                    ShowEmptyError();
                }
            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tasks.getTasks().clear();
                file = null;
                tasksLabel.setText("Tasks:");

                UpdateTable();
            }
        });
    }

    /**
     * Display error message when there are no tasks.
     */
    private void ShowEmptyError() {
        JOptionPane.showMessageDialog(new JFrame(), "Please add one or more tasks.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Show Save As dialog.
     */
    private void SaveAs() {

        // Show dialog and save the task to a file.
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save tasks");

        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("XML Files", "XML");
        jFileChooser.setFileFilter(fileNameExtensionFilter);

        int userSelection = jFileChooser.showSaveDialog(mainPanel);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String fileName = String.valueOf(jFileChooser.getSelectedFile());

            if (fileName != null) {

                if (!fileName.endsWith(".xml"))
                    fileName = fileName + ".xml";

                tasks.createXML(fileName);

                file = new File(fileName);
                tasksLabel.setText("Tasks (" + file + "):");
            }
        }
    }

    /**
     * Update tasks in table.
     */
    public void UpdateTable() {
        taskTableModel.fireTableDataChanged();
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Tasks");
        frame.setContentPane(new Main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(746, 340);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
