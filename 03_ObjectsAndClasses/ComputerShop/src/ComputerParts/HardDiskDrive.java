package ComputerParts;

import Helpers.HDDType;

public class HardDiskDrive {

    private final HDDType type; // тип
    private final int capacity; // объем
    private final double weight;  // вес

    public HardDiskDrive(HDDType type, int capacity, double weight) {
        this.type = type;
        this.capacity = capacity;
        this.weight = weight;
    }

    public HDDType getType() {
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
        return "=== Hard Disk Drive ===" +
                "\n\ttype = " + type +
                "\n\tcapacity = " + capacity +
                "\n\tweight = " + weight;
    }
}
