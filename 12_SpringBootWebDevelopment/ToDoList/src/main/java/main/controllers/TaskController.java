package main.controllers;

import main.BDEmulator.Storage;
import main.ControllerHelper;
import main.response.TaskRepository;
import main.response.Worker;
import main.response.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.response.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    private Storage storage;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private WorkerRepository workerRepository;

    @GetMapping("/tasks/")
    public ResponseEntity getAll() {
        List<Task> tasks = ControllerHelper.getTasksFromDB(repository);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Task list is empty");
        }
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Optional<Task> task = repository.findById(id);
        if (!task.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task with id = " + id);
        }
        return new ResponseEntity(task.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/tasks/", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity add(@RequestBody Task task) {
        Worker worker = task.getWorker();
        if (worker.getId() == null) {
            workerRepository.save(worker);
        }
        return new ResponseEntity(repository.save(task), HttpStatus.OK);
    }

    // одиночные методы PUT \ DELETE

    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@RequestBody Task taskDetails) {
        Optional<Task> taskOptional = repository.findById(taskDetails.getId());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setContext(taskDetails.getContext());
            task.setWorker(taskDetails.getWorker());
            return new ResponseEntity(repository.save(task), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task with id " + taskDetails.getId());
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            repository.delete(task.get());
            return new ResponseEntity(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No task with id " + id);
    }

    // массовые методы PUT \ DELETE

    @PutMapping("/tasks/")
    public ResponseEntity updateAll(@RequestBody Task taskDetails) {
        List<Task> tasks = ControllerHelper.getTasksFromDB(repository);
        if (tasks.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is empty");
        }
        for (Task task : tasks) {
            task.setContext(taskDetails.getContext());
            task.setWorker(taskDetails.getWorker());
            repository.save(task);
        }
        return new ResponseEntity(tasks, HttpStatus.OK);
    }


    @DeleteMapping("/tasks/")
    public ResponseEntity deleteAll() {
        repository.deleteAll();
        List<Task> deletedTaskList = ControllerHelper.getTasksFromDB(repository);
        if (deletedTaskList.isEmpty()) {
            return new ResponseEntity("Task list deleted", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting");
    }


}
