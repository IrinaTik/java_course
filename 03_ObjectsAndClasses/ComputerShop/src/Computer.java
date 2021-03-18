import ComputerParts.*;

public class Computer {

    private Processor processor;
    private OperativeMemory memory;
    private HardDiskDrive hdd;
    private Monitor monitor;
    private Keyboard keyboard;

    private final String vendor;
    private final String name;

    public Computer(String vendor, String name) {
        this.vendor = vendor;
        this.name = name;
    }

    public Computer(String name, String vendor, Processor processor, OperativeMemory memory, HardDiskDrive hdd, Monitor monitor, Keyboard keyboard) {
        this.name = name;
        this.vendor = vendor;
        this.processor = processor;
        this.memory = memory;
        this.hdd = hdd;
        this.monitor = monitor;
        this.keyboard = keyboard;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public OperativeMemory getMemory() {
        return memory;
    }

    public void setMemory(OperativeMemory memory) {
        this.memory = memory;
    }

    public HardDiskDrive getHdd() {
        return hdd;
    }

    public void setHdd(HardDiskDrive hdd) {
        this.hdd = hdd;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public double getFullWeight() {
        return processor.getWeight()
                + memory.getWeight()
                + hdd.getWeight()
                + monitor.getWeight()
                + keyboard.getWeight();
    }

    @Override
    public String toString() {
        return "Computer " + name + " by " + vendor +
                "\n" + processor.toString() +
                "\n" + memory.toString() +
                "\n" + hdd.toString() +
                "\n" + monitor.toString() +
                "\n" + keyboard.toString();
    }
}
