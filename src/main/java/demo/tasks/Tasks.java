package demo.tasks;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdalo on 30-9-2015.
 */
class Tasks {
    private final List<Task> tasks;

    public Tasks() {
        tasks = new ArrayList<Task>();
    }

    /**
     * Get all tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Create XML file from task.
     *
     * @param file Destination path and filename.
     */
    public void createXML(String file) {

        // Create XML file using JDOM library.
        Element tasksElement = new Element("tasks");
        Document document = new Document(tasksElement);

        for (int i = 0; i < tasks.size(); i++) {
            Element taskElement = new Element("task");
            taskElement.setAttribute(new Attribute("id", Integer.toString(i)));
            taskElement.addContent(new Element("name").setText(tasks.get(i).getName()));
            taskElement.addContent(new Element("deadline").setText(tasks.get(i).getDeadline()));
            taskElement.addContent(new Element("priority").setText(Integer.toString(tasks.get(i).getPriority())));
            taskElement.addContent(new Element("status").setText(Integer.toString(tasks.get(i).getStatus())));
            taskElement.addContent(new Element("description").setText(tasks.get(i).getDescription()));

            document.getRootElement().addContent(taskElement);
        }

        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());

        try {
            xmlOutputter.output(document, new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read tasks from file.
     *
     * @param file Source path and filename.
     */
    public void readXML(File file) {

        tasks.clear();

        // Read XML using JDOM library.
        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(String.valueOf(file));

        Document document;

        try {
            document = saxBuilder.build(xmlFile);

            Element rootElement = document.getRootElement();
            List elementList = rootElement.getChildren("task");

            for (Object anElementList : elementList) {

                Element node = (Element) anElementList;

                tasks.add(new Task(node.getChildText("name"), node.getChildText("deadline"), Integer.parseInt(node.getChildText("priority")), Integer.parseInt(node.getChildText("status")), node.getChildText("description")));
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
