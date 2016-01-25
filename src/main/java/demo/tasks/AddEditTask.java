package demo.tasks;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AddEditTask extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JTextField textFieldDeadline;
    private JComboBox<String> comboBoxStatus;
    private JTextArea textAreaDescription;
    private JComboBox<String> comboBoxPriority;
    private final List<Task> tasks;
    private final Main main;
    private final Integer id;

    public AddEditTask(List<Task> tasks, Main main, Integer id) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.tasks = tasks;
        this.main = main;
        this.id = id;

        // Set Combo Box items.
        comboBoxStatus.addItem("Pending");
        comboBoxStatus.addItem("Done");
        comboBoxStatus.addItem("WIP");

        comboBoxPriority.addItem("High");
        comboBoxPriority.addItem("Normal");
        comboBoxPriority.addItem("Low");

        if (id != null) {

            // Load data if existing task is loaded.
            textFieldName.setText(tasks.get(id).getName());
            textFieldDeadline.setText(tasks.get(id).getDeadline());
            comboBoxPriority.setSelectedIndex(tasks.get(id).getPriority());
            comboBoxStatus.setSelectedIndex(tasks.get(id).getStatus());
            textAreaDescription.setText(tasks.get(id).getDescription());
        } else {
            // Set date if a task is created.
            textFieldDeadline.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here

        List<String> errors = new ArrayList<String>();

        // Check user input for empty task name.
        if (Objects.equals(textFieldName.getText(), "")) {
            errors.add("- Task name is empty");
        }

        // Check user input for wrong formatted date.
        Pattern pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)");
        Matcher matcher = pattern.matcher(textFieldDeadline.getText());

        if (matcher.matches()) {
            if (!validDate(textFieldDeadline.getText())) {
                errors.add("- Date is not valid");
            }
        } else {
            errors.add("- Date is not in format dd-mm-yyy");
        }

        if (errors.size() == 0) {
            if (id != null) {
                tasks.set(id, new Task(textFieldName.getText(), textFieldDeadline.getText(), comboBoxPriority.getSelectedIndex(), comboBoxStatus.getSelectedIndex(), textAreaDescription.getText()));

            } else {
                tasks.add(new Task(textFieldName.getText(), textFieldDeadline.getText(), comboBoxPriority.getSelectedIndex(), comboBoxStatus.getSelectedIndex(), textAreaDescription.getText()));
            }
        } else {

            // Show error message when user input is not valid.
            String errorMessage = "Please resolve the following problem(s):\n";

            for (int i = 0; i < errors.size(); i++) {
                errorMessage = errorMessage + errors.get(i);

                if (i + 1 < errors.size()) {
                    errorMessage = errorMessage + "\n";
                }
            }

            JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (errors.size() == 0) {
            main.UpdateTable();

            dispose();
        }
    }

    private boolean validDate(String date) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");

        try {
            DateTime dateTime = dateTimeFormatter.parseDateTime(date);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        AddEditTask dialog = new AddEditTask(null, null, null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
