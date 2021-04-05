package BDEmulator;

import response.Task;

import java.util.*;

public class Storage {

    private static int currentId = 1;
    private static Map<Integer, Task> tasks = new HashMap<>();
    private static Set<Integer> freeIds = new HashSet<>(); // идентификаторы, освободившиеся в результате удаления элементов

    public static int addTask(Task task) {
        int id;
        if (freeIds.isEmpty()) {
            id = currentId++;
        } else {
            id = freeIds.stream().findFirst().get();
            freeIds.remove(id);
        }
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
            return tasks.get(id);
        }
        return null;
    }

    public static Task updateTask(int id, Task taskDetails) {
        Task task = getTask(id);
        if (task != null) {
            task.setContext(taskDetails.getContext());
            task.setWorkerId(taskDetails.getWorkerId());
            return task;
        }
        return null;
    }

    public static boolean deleteTask(int id) {
        Task task = getTask(id);
        if (task != null) {
            tasks.remove(id);
            freeIds.add(id);
            return true;
        }
        return false;
    }
}
