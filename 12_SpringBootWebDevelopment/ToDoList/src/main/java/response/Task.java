package response;

import java.util.Objects;

public class Task {

    private int id;
    private String context;
    private int workerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                workerId == task.workerId &&
                Objects.equals(context, task.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, context, workerId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", context='" + context + '\'' +
                ", workerId=" + workerId +
                '}';
    }
}
