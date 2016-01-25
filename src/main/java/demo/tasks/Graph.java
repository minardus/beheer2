package demo.tasks;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

class Graph extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JButton buttonCancel;
    private JPanel panelGraph;
    private final List<Task> tasks;

    /**
     * Generate graph with task status data.
     */
    private void AddGraph() {

        float pending = 0;
        float done = 0;
        float wip = 0;

        // Calculate percentage.
        for (Task task : tasks) {
            if (task.getStatus() == 0) {
                pending = pending + 1;
            }
            if (task.getStatus() == 1) {
                done = done + 1;
            }
            if (task.getStatus() == 2) {
                wip = wip + 1;
            }
        }

        //Set chart data.
        DefaultPieDataset defaultPieDataset = new DefaultPieDataset();
        defaultPieDataset.setValue("Pending", (pending / tasks.size()) * 100);
        defaultPieDataset.setValue("Done", (done / tasks.size()) * 100);
        defaultPieDataset.setValue("WIP", (wip / tasks.size()) * 100);

        // Create chart.
        JFreeChart jFreeChart = ChartFactory.createPieChart3D("Tasks", defaultPieDataset, true, true, false);
        PiePlot3D piePlot3D = (PiePlot3D) jFreeChart.getPlot();
        piePlot3D.setStartAngle(250);
        piePlot3D.setDirection(Rotation.CLOCKWISE);
        piePlot3D.setForegroundAlpha(0.4f);
        piePlot3D.setCircular(true);

        ChartPanel chartPanel = new ChartPanel(jFreeChart);

        panelGraph.setLayout(new BoxLayout(panelGraph, BoxLayout.PAGE_AXIS));
        panelGraph.add(chartPanel);
        panelGraph.validate();
    }

    public Graph(List<Task> tasks) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonClose);

        this.tasks = tasks;

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

        AddGraph();
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Graph dialog = new Graph(null);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
