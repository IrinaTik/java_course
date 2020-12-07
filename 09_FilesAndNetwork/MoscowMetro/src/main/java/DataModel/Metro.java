package DataModel;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Metro {

    private Set<Line> lines;

    public Metro() {
        this.lines = new TreeSet<>();
    }

    public Line getLine(String number) {
        return this.lines.stream().filter(line -> line.getNumber().equals(number)).findAny().orElse(null);
    }

    public Set<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void output() {
        for (Line line : this.getLines()) {
            System.out.println(line.toString());
            for (Station station : line.getStations()) {
                System.out.println("\t" + station.toString());
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metro metro = (Metro) o;
        return lines.stream().allMatch(line -> metro.getLines().stream().anyMatch(otherLine -> otherLine.equals(line)));
//        return Objects.equals(lines, metro.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }
}
