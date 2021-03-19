import ComputerParts.*;
import Helpers.*;

import java.util.ArrayList;
import java.util.List;

public class ShopView {

    private static String[] names = {"CityLink", "MegaBlink", "SuperGames"};
    private static String[] vendors = {"ASUS", "DELL", "ACER"};

    private static List<Processor> processors = generateProcessors();
    private static List<OperativeMemory> memoryBlocks = generateMemoryBlocks();
    private static List<HardDiskDrive> hdds = generateHDDs();
    private static List<Monitor> monitors = generateMonitors();
    private static List<Keyboard> keyboards = generateKeyboards();

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Computer computer = new Computer(names[i], vendors[i], processors.get(0), memoryBlocks.get(0), hdds.get(i), monitors.get(i), keyboards.get(i));
            System.out.println(computer);
            System.out.println("Full weight: " + computer.getFullWeight()  + "\n");
        }
    }

    private static List<Processor> generateProcessors() {
        List<Processor> processorList = new ArrayList<>();
        processorList.add(new Processor(3.4, 4, ProcessorLabel.AMD, 0.3));
        processorList.add(new Processor(3.0, 2, ProcessorLabel.INTEL, 0.2));
        processorList.add(new Processor(3.5, 4, ProcessorLabel.INTEL, 0.3));
        return processorList;
    }
    private static List<OperativeMemory> generateMemoryBlocks() {
        List<OperativeMemory> memoryList = new ArrayList<>();
        memoryList.add(new OperativeMemory(MemoryType.DDR3, 2, 0.1));
        memoryList.add(new OperativeMemory(MemoryType.DDR3L, 4, 0.1));
        memoryList.add(new OperativeMemory(MemoryType.DDR4, 16, 0.1));
        return memoryList;
    }

    private static List<HardDiskDrive> generateHDDs() {
        List<HardDiskDrive> hddList = new ArrayList<>();
        hddList.add(new HardDiskDrive(HDDType.HDD, 500, 1));
        hddList.add(new HardDiskDrive(HDDType.HDD, 500, 1));
        hddList.add(new HardDiskDrive(HDDType.HDD, 500, 1));
        return hddList;
    }

    private static List<Monitor> generateMonitors() {
        List<Monitor> monitorList = new ArrayList<>();
        monitorList.add(new Monitor(MonitorType.IPS, 18.5, 2.7));
        monitorList.add(new Monitor(MonitorType.TN, 23.8, 3.1));
        monitorList.add(new Monitor(MonitorType.VA, 23.6, 3.0));
        return monitorList;
    }

    private static List<Keyboard> generateKeyboards() {
        List<Keyboard> keyboardList = new ArrayList<>();
        keyboardList.add(new Keyboard(KeyboardType.FULL, KeyboardHasLights.NO, 1.0));
        keyboardList.add(new Keyboard(KeyboardType.GAME, KeyboardHasLights.NO, 1.1));
        keyboardList.add(new Keyboard(KeyboardType.GAME, KeyboardHasLights.YES, 1.3));
        return keyboardList;
    }
}
