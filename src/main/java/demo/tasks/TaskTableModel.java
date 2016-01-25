package demo.tasks;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by wdalo on 30-9-2015.
 */
class TaskTableModel extends AbstractTableModel {

    private final List<Task> tasks;
    private final String[] columnNames = new String[]{"Task", "Deadline", "Priority", "Status"};

    public TaskTableModel(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void removeTask(int task) {

        tasks.remove(task);
        fireTableRowsDeleted(task, task);
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object value = "Unknown";
        Task task = tasks.get(rowIndex);
        if (columnIndex == 0) {
            value = task.getName();
        } else if (columnIndex == 1) {
            value = task.getDeadline();

        } else if (columnIndex == 2) {

            if (task.getPriority() == 0) {
                value = "High";
            }
            if (task.getPriority() == 1) {
                value = "Normal";
            }
            if (task.getPriority() == 2) {
                value = "Low";
            }

        } else if (columnIndex == 3) {
            if (task.getStatus() == 0) {
                value = "Pending";
            }
            if (task.getStatus() == 1) {
                value = "Done";
            }
            if (task.getStatus() == 2) {
                value = "WIP";
            }
        }

        return value;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
