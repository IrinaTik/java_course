package main.BDEmulator;

import main.response.Worker;
import org.springframework.stereotype.Component;
import main.response.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.stream;

@Component("storage")
public class Storage {

    private AtomicInteger currentTaskId = new AtomicInteger(1);
    private AtomicInteger currentWorkerId = new AtomicInteger(1);
    private Map<Integer, Task> tasks = new ConcurrentHashMap<>();
    private Map<Integer, Worker> workers = new ConcurrentHashMap<>();

    public int addTask(Task task) {
        int id;
        id = currentTaskId.getAndIncrement();
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
            task.setWorker(taskDetails.getWorker());
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

    public Integer addWorker(Worker worker) {
        Integer id = currentWorkerId.getAndIncrement();
        worker.setId(id);
        workers.put(id, worker);
        return id;
    }

    public Worker getWorker(Integer id) {
        if (workers.containsKey(id)) {
            return workers.get(id);
        }
        return null;
    }

    public Worker updateWorker(Integer id, Worker workerDetails) {
        Worker worker = getWorker(id);
        if (worker != null) {
            worker.setName(workerDetails.getName());
            worker.setExpertise(workerDetails.getExpertise());
            return worker;
        }
        return null;
    }

    public List<Worker> getAllWorkers() {
        List<Worker> workerList = new ArrayList<>();
        workerList.addAll(workers.values());
        return workerList;
    }

    public boolean deleteWorker(Integer id) {
        Worker worker = getWorker(id);
        if (worker != null) {
            workers.remove(id);
            // из всех заданий удаляем информацию об этом исполнителе
            getAllTasks().stream().filter(task -> worker.equals(task.getWorker())).forEach(task -> task.setWorker(null));
            return true;
        }
        return false;
    }

}
