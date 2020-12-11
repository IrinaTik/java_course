package DataModel;

import java.util.Objects;

public class Line implements Comparable<Line> {

    private String name;
    private String number;

    public Line() {}

    public Line(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }


    public String getNumber() {
        return number;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(name, line.name) &&
                Objects.equals(number, line.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number);
    }

    @Override
    public String toString() {
        return name + " - " + number;
    }

    @Override
    public int compareTo(Line o) {
        if (this.getNumber().length() > o.getNumber().length()) {
            return 1;
        } else if (this.getNumber().length() < o.getNumber().length()) {
            return -1;
        } else {
            return this.getNumber().compareTo(o.getNumber());
        }
    }
}
