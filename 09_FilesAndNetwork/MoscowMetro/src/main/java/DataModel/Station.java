package DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Station implements Comparable<Station>{

    private String name;
    @JsonProperty("line")
    private String lineNumber;

    public Station() {
    }

    public Station(String name, String lineNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(name, station.name) &&
                Objects.equals(lineNumber, station.lineNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lineNumber);
    }

    @Override
    public String toString() {
        return this.name + "(" + this.lineNumber + ")";
    }

    @Override
    public int compareTo(Station s) {
        if (this.getLineNumber().length() > s.getLineNumber().length()) {
            return 1;
        } else if (this.getLineNumber().length() < s.getLineNumber().length()) {
            return -1;
        } else {
            return this.getLineNumber().compareTo(s.getLineNumber());
        }
    }
}
