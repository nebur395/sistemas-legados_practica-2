package models;

/**
 * Container Object of a Task
 */
public class TaskVO {
    /**
     * Description of the Task
     */
    private String description;
    /**
     * Date of the task. A string in format DDMM (Day, Month)
     */
    private String date;
    /**
     * Asignee of the task. If its a General Task, this String is empty
     */
    private String assignee;
    /**
     * Type of the task. Its true if the task is general, false if its specific
     */
    private boolean type;

    /**
     * Constructor that creates a specific Task
     *
     * @param description Description of the Task
     * @param date        Date of the Task
     * @param assignee     Asignee of the Task
     */
    public TaskVO(String description, String date, String assignee) {
        this.description = description;
        this.date = date;
        this.assignee = assignee;
        this.type = false;
    }

    /**
     * Constructor that creates a general Task
     *
     * @param description Description of the Task
     * @param date        Date of the Task
     */
    public TaskVO(String description, String date) {
        this.description = description;
        this.date = date;
        this.assignee = "";
        this.type = true;
    }

    /**
     * Returns the description of the class
     *
     * @return Description of the class
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the Description of the Task
     *
     * @param description New description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date of the class
     *
     * @return Date of the class
     */
    public String getDate() {
        return date;
    }

    /**
     * Changes the Date of the Task
     *
     * @param date New date, in the format DDMM
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the Asignee of the class. If this is a
     *
     * @return Description of the class
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * Changes the Asignee of the Task. Passing an empty String will convert the class into a
     * general Task, passing a non-empty String will convert the class to an specific Task.
     *
     * @param assignee New Asignee
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    /**
     * Returns if the class represents a general Task
     *
     * @return true if the class is an general Task, false if its a specific
     */
    public boolean isGeneral() {
        return type;
    }
}
