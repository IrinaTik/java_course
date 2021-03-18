package ComputerParts;

import Helpers.KeyboardHasLights;
import Helpers.KeyboardType;

public class Keyboard {

    private final KeyboardType type; // тип
    private final KeyboardHasLights lights; // подсветка
    private final double weight;  // вес

    public Keyboard(KeyboardType type, KeyboardHasLights lights, double weight) {
        this.type = type;
        this.lights = lights;
        this.weight = weight;
    }

    public KeyboardType getType() {
        return type;
    }

    public KeyboardHasLights getLights() {
        return lights;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "=== Keyboard ===" +
                "\n\ttype = " + type +
                "\n\twith lights = " + lights +
                "\n\tweight = " + weight;
    }
}
