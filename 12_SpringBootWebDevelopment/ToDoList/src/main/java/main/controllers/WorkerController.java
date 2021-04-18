package main.controllers;

import main.BDEmulator.Storage;
import main.response.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WorkerController {

    @Autowired
    private Storage storage;

    @GetMapping("/workers/")
    public ResponseEntity getAll() {
        if (storage.getAllWorkers().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Worker list is empty");
        }
        return new ResponseEntity(storage.getAllWorkers(), HttpStatus.OK);
    }

    @GetMapping("/workers/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Worker worker = storage.getWorker(id);
        if (worker == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No worker with id = " + id);
        }
        return new ResponseEntity(worker, HttpStatus.OK);
    }

    @PostMapping("/workers/{id}")
    public ResponseEntity add(@RequestBody Worker worker) {
        return new ResponseEntity(storage.addWorker(worker), HttpStatus.OK);
    }

    @PutMapping("/workers/{id}")
    public ResponseEntity updateOne(@RequestBody Worker workerDetails) {
        Worker worker = storage.updateWorker(workerDetails.getId(), workerDetails);
        if (worker != null) {
            return new ResponseEntity(worker, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No worker with id = " + workerDetails.getId());
    }

    @DeleteMapping("/workers/{id}")
    public ResponseEntity deleteOne(@PathVariable Integer id) {
        boolean isRemoved = storage.deleteWorker(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(id, HttpStatus.OK);
    }

    @PutMapping("/workers/")
    public ResponseEntity updateAll(@RequestBody Worker workerDetails) {
        int workerCount = storage.getAllWorkers().size();
        if (workerCount == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is empty");
        }
        List<Worker> updatedWorkers = new ArrayList<>();
        for (int i = 0; i < workerCount; i++) {
            Worker worker = storage.updateWorker(i, workerDetails);
            if (worker != null) {
                updatedWorkers.add(worker);
            }
        }
        return new ResponseEntity(updatedWorkers, HttpStatus.OK);
    }


    @DeleteMapping("/workers/")
    public ResponseEntity deleteAll() {
        List<Worker> workerList = storage.getAllWorkers();
        if (workerList.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker list is already empty");
        }
        int lastIndex = workerList.get(workerList.size() - 1).getId();
        for (int i = 0; i <= lastIndex; i++) {
            storage.deleteWorker(i);
        }
        if (storage.getAllTasks().size() == 0) {
            return new ResponseEntity("Worker list deleted", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting");
    }

}
