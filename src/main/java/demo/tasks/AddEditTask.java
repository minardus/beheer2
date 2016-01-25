package demo.tasks;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class AddEditTask extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldName;
    private JComboBox<String> comboBoxStatus;
    private JTextArea textAreaDescription;
    private JComboBox<String> comboBoxPriority;
    private JDatePickerImpl jDatePickerImplDeadline;
    private JDatePanelImpl jDatePanelImpl;
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
            jDatePickerImplDeadline.setTextEditable(true);

            String date[] = tasks.get(id).getDeadline().split("-");

            jDatePanelImpl.getModel().setDate(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0]));
            jDatePanelImpl.getModel().setSelected(true);
            jDatePickerImplDeadline.setTextEditable(false);


            comboBoxPriority.setSelectedIndex(tasks.get(id).getPriority());
            comboBoxStatus.setSelectedIndex(tasks.get(id).getStatus());
            textAreaDescription.setText(tasks.get(id).getDescription());
        }else {

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            jDatePanelImpl.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
            jDatePanelImpl.getModel().setSelected(true);
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
        if (errors.size() == 0) {
            if (id != null) {
                tasks.set(id, new Task(textFieldName.getText(), Integer.toString(jDatePickerImplDeadline.getModel().getDay()) + "-" + Integer.toString(jDatePickerImplDeadline.getModel().getMonth() + 1) + "-" + Integer.toString(jDatePickerImplDeadline.getModel().getYear()), comboBoxPriority.getSelectedIndex(), comboBoxStatus.getSelectedIndex(), textAreaDescription.getText()));

            } else {
                tasks.add(new Task(textFieldName.getText(), Integer.toString(jDatePickerImplDeadline.getModel().getDay()) + "-" + Integer.toString(jDatePickerImplDeadline.getModel().getMonth() + 1) + "-" + Integer.toString(jDatePickerImplDeadline.getModel().getYear()), comboBoxPriority.getSelectedIndex(), comboBoxStatus.getSelectedIndex(), textAreaDescription.getText()));
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

    private void createUIComponents() {
        UtilDateModel utilDateModel = new UtilDateModel();
        jDatePanelImpl = new JDatePanelImpl(utilDateModel);
        jDatePickerImplDeadline = new JDatePickerImpl(jDatePanelImpl);
    }
}
