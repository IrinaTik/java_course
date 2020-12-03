package DataModel;

import java.util.ArrayList;
import java.util.List;

public class Metro {

    private List<Line> lines;

    public Metro() {
        this.lines = new ArrayList<>();
    }

    public Line getLine(String number) {
        return this.lines.stream().filter(line -> line.getNumber().equals(number)).findAny().orElse(null);
    }

    public List<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void addSeveralLines(List<Line> lines) {
        this.lines.addAll(lines);
    }
}
