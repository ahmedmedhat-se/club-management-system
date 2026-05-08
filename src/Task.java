import java.util.Date;

public class Task {

    private String taskId;
    private String title;
    private String assignedTo;
    private String status;
    private Date dueDate;

    public Task(String taskId, String title, String assignedTo, Date dueDate) {
        this.taskId     = taskId;
        this.title      = title;
        this.assignedTo = assignedTo;
        this.dueDate    = dueDate;
        this.status     = "PENDING";
    }

    public void complete() {
        this.status = "DONE";
    }

    public boolean isOverdue() {
        if (dueDate == null) return false;
        if (status.equals("DONE")) return false;
        return dueDate.before(new Date());
    }

    public String getTaskId()               { return taskId; }
    public String getTitle()                { return title; }
    public String getAssignedTo()           { return assignedTo; }
    public String getStatus()               { return status; }
    public Date getDueDate()                { return dueDate; }

    public void setTaskId(String taskId)    { this.taskId = taskId; }
    public void setTitle(String title)      { this.title = title; }
    public void setAssignedTo(String userId){ this.assignedTo = userId; }
    public void setStatus(String status)    { this.status = status; }
    public void setDueDate(Date dueDate)    { this.dueDate = dueDate; }

    @Override
    public String toString() {
        return "[" + status + "] " + title;
    }
}