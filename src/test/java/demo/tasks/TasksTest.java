package demo.tasks;

import junit.framework.TestCase;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.util.List;

/**
 * Created by wdalo on 11-10-2015.
 */
public class TasksTest extends TestCase {

    public void testGetTasks() throws Exception {
        Tasks tasks = new Tasks();
        tasks.getTasks().add(new Task("Test task 1", "13-04-2015", 1, 0, "Een omschrijving 1."));
        tasks.getTasks().add(new Task("Test task 2", "21-06-2015", 2, 1, "Een omschrijving 2."));

        int taskCount = tasks.getTasks().size();

        assertEquals(2, taskCount);
    }

    public void testXMLExportImport() throws Exception {

        // Add sample tasks.
        Tasks tasks = new Tasks();
        tasks.getTasks().add(new Task("Test task 1", "13-04-2015", 1, 0, "Een omschrijving 1."));
        tasks.getTasks().add(new Task("Test task 2", "21-06-2015", 2, 1, "Een omschrijving 2."));

        File filename = new File(System.getProperty("user.home") + "\\tasks_test.xml");

        // Save tasks to XML file.
        tasks.createXML(filename.toString());

        tasks.getTasks().clear();

        // Read tasks back from XML file.
        tasks.readXML(filename);

        int taskCount = tasks.getTasks().size();

        assertEquals(2, taskCount);

        // Check task details.
        assertEquals(tasks.getTasks().get(0).getName(), "Test task 1");
        assertEquals(tasks.getTasks().get(0).getDeadline(), "13-04-2015");
        assertEquals(tasks.getTasks().get(0).getPriority(), 1);
        assertEquals(tasks.getTasks().get(0).getStatus(), 0);
        assertEquals(tasks.getTasks().get(0).getDescription(), "Een omschrijving 1.");

        assertEquals(tasks.getTasks().get(1).getName(), "Test task 2");
        assertEquals(tasks.getTasks().get(1).getDeadline(), "21-06-2015");
        assertEquals(tasks.getTasks().get(1).getPriority(), 2);
        assertEquals(tasks.getTasks().get(1).getStatus(), 1);
        assertEquals(tasks.getTasks().get(1).getDescription(), "Een omschrijving 2.");
    }
}