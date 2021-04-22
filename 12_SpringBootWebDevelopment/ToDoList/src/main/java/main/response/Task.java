package main.response;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String context;

    @OneToOne(cascade = CascadeType.ALL)
    private Worker worker;

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

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                worker == task.worker &&
                Objects.equals(context, task.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, context, worker);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", context='" + context + '\'' +
                worker +
                '}';
    }
}
