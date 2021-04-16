package main;

import BDEmulator.Storage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.Task;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    @GetMapping("/tasks/")
    public List<Task> list() {
        return Storage.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id) {
        Task task =  Storage.getTask(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @PostMapping("/tasks/")
    public int add(Task task) {
        return Storage.addTask(task);
    }

    // одиночные методы PUT \ DELETE

    @PutMapping("/tasks/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody Task taskDetails) {
        Task task = Storage.updateTask(id, taskDetails);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(task, HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        boolean isRemoved = Storage.deleteTask(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    // массовые методы PUT \ DELETE

    @PutMapping("/tasks/")
    public ResponseEntity updateAll(@RequestBody Task taskDetails) {
        int taskCount = Storage.getAllTasks().size();
        if (taskCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is empty");
        }
        List<Task> updatedTasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Task task = Storage.updateTask(i, taskDetails);
            if (task != null) {
                updatedTasks.add(task);
            }
        }
        return new ResponseEntity(updatedTasks, HttpStatus.OK);
    }


    @DeleteMapping("/tasks/")
    public ResponseEntity deleteAll() {
        List<Task> taskList = Storage.getAllTasks();
        if (taskList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is already empty");
        }
        int lastIndex = taskList.get(taskList.size() - 1).getId();
        for (int i = 0; i <= lastIndex; i++) {
            Storage.deleteTask(i);
        }
        if (Storage.getAllTasks().size() == 0) {
            return new ResponseEntity("0", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting");
    }


}
