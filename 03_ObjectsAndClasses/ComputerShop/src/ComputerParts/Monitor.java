package ComputerParts;

import Helpers.MonitorType;

public class Monitor {

    private final MonitorType type; // тип
    private final double size; // диагональ
    private final double weight;  // вес

    public Monitor(MonitorType type, double size, double weight) {
        this.type = type;
        this.size = size;
        this.weight = weight;
    }

    public MonitorType getType() {
        return type;
    }

    public double getSize() {
        return size;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "=== Monitor ===" +
                "\n\ttype = " + type +
                "\n\tsize = " + size +
                "\n\tweight = " + weight;
    }
}
