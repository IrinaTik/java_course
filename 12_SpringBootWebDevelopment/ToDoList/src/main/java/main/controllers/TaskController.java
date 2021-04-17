package main.controllers;

import main.BDEmulator.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.response.Task;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private Storage storage;

    @GetMapping("/tasks/")
    public List<Task> list() {
        return storage.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Task task =  storage.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PostMapping(value = "/tasks/", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public int add(@RequestBody Task task) {
        return storage.addTask(task);
    }

    // одиночные методы PUT \ DELETE

    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@RequestBody Task taskDetails) {
        Task task = storage.updateTask(taskDetails.getId(), taskDetails);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean isRemoved = storage.deleteTask(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    // массовые методы PUT \ DELETE

    @PutMapping("/tasks/")
    public ResponseEntity updateAll(@RequestBody Task taskDetails) {
        int taskCount = storage.getAllTasks().size();
        if (taskCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is empty");
        }
        List<Task> updatedTasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Task task = storage.updateTask(i, taskDetails);
            if (task != null) {
                updatedTasks.add(task);
            }
        }
        return new ResponseEntity(updatedTasks, HttpStatus.OK);
    }


    @DeleteMapping("/tasks/")
    public ResponseEntity deleteAll() {
        List<Task> taskList = storage.getAllTasks();
        if (taskList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is already empty");
        }
        int lastIndex = taskList.get(taskList.size() - 1).getId();
        for (int i = 0; i <= lastIndex; i++) {
            storage.deleteTask(i);
        }
        if (storage.getAllTasks().size() == 0) {
            return new ResponseEntity("0", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting");
    }


}
