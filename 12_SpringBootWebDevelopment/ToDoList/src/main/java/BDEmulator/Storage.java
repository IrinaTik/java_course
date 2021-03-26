package BDEmulator;

import response.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {

    private static int currentId = 1;
    private static Map<Integer, Task> tasks = new HashMap<>();

    public static int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public static List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(tasks.values());
        return taskList;
    }

    public static Task getTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.get(id);
        }
        return null;
    }
}
