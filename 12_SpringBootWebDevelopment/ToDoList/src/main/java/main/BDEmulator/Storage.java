package main.BDEmulator;

import org.springframework.stereotype.Component;
import main.response.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component("storage")
public class Storage {

    private AtomicInteger currentId = new AtomicInteger(1);
    private Map<Integer, Task> tasks = new ConcurrentHashMap<>();

    public int addTask(Task task) {
        int id;
        id = currentId.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(tasks.values());
        return taskList;
    }

    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    public Task updateTask(int id, Task taskDetails) {
        Task task = getTask(id);
        if (task != null) {
            task.setContext(taskDetails.getContext());
            task.setWorkerId(taskDetails.getWorkerId());
            return task;
        }
        return null;
    }

    public boolean deleteTask(int id) {
        Task task = getTask(id);
        if (task != null) {
            tasks.remove(id);
            return true;
        }
        return false;
    }

}
