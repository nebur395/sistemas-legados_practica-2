package models;

public class TaskVO {
    private String description;
    private String date;
    private String asignee;
    // True: general task
    // False: specific task
    private boolean type;

    public TaskVO(String description, String date, String asignee) {
        this.description = description;
        this.date = date;
        this.asignee = asignee;
        this.type = false;
    }

    public TaskVO(String description, String date) {
        this.description = description;
        this.date = date;
        this.asignee = "";
        this.type = true;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getAsignee() {
        return asignee;
    }

    public boolean isGeneral() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
