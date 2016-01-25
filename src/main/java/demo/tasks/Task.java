package demo.tasks;

/**
 * Created by wdalo on 30-9-2015.
 */
class Task {

    private final String name;
    private final String deadline;
    private final int status;
    private final int priority;
    private final String description;

    public Task(String name, String deadline, int priority, int status, String description) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.status = status;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDeadline() {
        return deadline;
    }

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

}
