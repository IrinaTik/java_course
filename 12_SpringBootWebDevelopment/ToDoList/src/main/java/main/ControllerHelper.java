package main;

import main.response.Task;
import main.response.TaskRepository;
import main.response.Worker;
import main.response.WorkerRepository;

import java.util.ArrayList;
import java.util.List;

public class ControllerHelper {

    public static List<Task> getTasksFromDB(TaskRepository taskRepository) {
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> tasks = new ArrayList<>();
        taskIterable.forEach(tasks::add);
        return tasks;
    }

    public static List<Worker> getWorkersFromDB(WorkerRepository workerRepository) {
        Iterable<Worker> workerIterable = workerRepository.findAll();
        List<Worker> workers = new ArrayList<>();
        workerIterable.forEach(workers::add);
        return workers;
    }
}
