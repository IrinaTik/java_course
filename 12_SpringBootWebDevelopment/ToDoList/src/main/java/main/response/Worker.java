package main.response;

import java.util.Objects;

public class Worker {

    private Integer id;
    private String name;
    private String expertise;

    public Worker() {
    }

    public Worker(Integer id, String name, String expertise) {
        this.id = id;
        this.name = name;
        this.expertise = expertise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return id == worker.id &&
                Objects.equals(name, worker.name) &&
                Objects.equals(expertise, worker.expertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, expertise);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expertise='" + expertise + '\'' +
                '}';
    }
}
