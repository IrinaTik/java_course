package ComputerParts;

import Helpers.MemoryType;

public class OperativeMemory {

    private final MemoryType type; // тип
    private final int capacity; // объем
    private final double weight;  // вес

    public OperativeMemory(MemoryType type, int capacity, double weight) {
        this.type = type;
        this.capacity = capacity;
        this.weight = weight;
    }

    public MemoryType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "=== Operative Memory ===" +
                "\n\ttype = " + type +
                "\n\tcapacity = " + capacity +
                "\n\tweight = " + weight;
    }
}
