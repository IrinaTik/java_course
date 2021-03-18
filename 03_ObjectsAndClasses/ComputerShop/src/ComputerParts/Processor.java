package ComputerParts;

import Helpers.ProcessorLabel;

public class Processor {

    private final double frequency; // частота
    private final int coreCount; // количество ядер
    private final ProcessorLabel processorLabel;  // производитель
    private final double weight;  // вес

    public Processor(double frequency, int coreCount, ProcessorLabel processorLabel, double weight) {
        this.frequency = frequency;
        this.coreCount = coreCount;
        this.processorLabel = processorLabel;
        this.weight = weight;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getCoreCount() {
        return coreCount;
    }

    public ProcessorLabel getProcessorLabel() {
        return processorLabel;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "=== Processor ===" +
                "\n\tfrequency = " + frequency +
                "\n\tcore count = " + coreCount +
                "\n\tprocessor label = " + processorLabel +
                "\n\tweight = " + weight;
    }
}
