package main.controllers;

import main.BDEmulator.Storage;
import main.ControllerHelper;
import main.response.Task;
import main.response.TaskRepository;
import main.response.Worker;
import main.response.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class WorkerController {

    @Autowired
    private Storage storage;

    @Autowired
    private WorkerRepository repository;

    @Autowired
    private TaskRepository taskRepository;


    @GetMapping("/workers/")
    public ResponseEntity getAll() {
        List<Worker> workers = ControllerHelper.getWorkersFromDB(repository);
        if (workers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Worker list is empty");
        }
        return new ResponseEntity(workers, HttpStatus.OK);
    }

    @GetMapping("/workers/{id}")
    public ResponseEntity getOne(@PathVariable Integer id) {
        Optional<Worker> worker = repository.findById(id);
        if (!worker.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No worker with id = " + id);
        }
        return new ResponseEntity(worker.get(), HttpStatus.OK);
    }

    @PostMapping("/workers/{id}")
    public ResponseEntity add(@RequestBody Worker worker) {
        return new ResponseEntity(repository.save(worker), HttpStatus.OK);
    }

    @PutMapping("/workers/{id}")
    public ResponseEntity updateOne(@RequestBody Worker workerDetails) {
        Optional<Worker> workerOptional = repository.findById(workerDetails.getId());
        if (workerOptional.isPresent()) {
            Worker worker = workerOptional.get();
            worker.setName(workerDetails.getName());
            worker.setExpertise(workerDetails.getExpertise());
            return new ResponseEntity(repository.save(worker), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No worker with id = " + workerDetails.getId());
    }

    @DeleteMapping("/workers/{id}")
    public ResponseEntity deleteOne(@PathVariable Integer id) {
        Optional<Worker> worker = repository.findById(id);
        if (worker.isPresent()) {
            List<Task> tasks = ControllerHelper.getTasksFromDB(taskRepository);
            tasks.stream()
                    .filter(task -> (task.getWorker() != null))
                    .filter(task -> task.getWorker().equals(worker.get()))
                    .forEach(task -> {
                        task.setWorker(null);
                        taskRepository.save(task);
                    });
            repository.delete(worker.get());
            return new ResponseEntity(id, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No worker with id " + id);
    }

    @PutMapping("/workers/")
    public ResponseEntity updateAll(@RequestBody Worker workerDetails) {
        List<Worker> workers = ControllerHelper.getWorkersFromDB(repository);
        if (workers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker list is empty");
        }
        for (Worker worker : workers) {
            worker.setName(workerDetails.getName());
            worker.setExpertise(workerDetails.getExpertise());
            repository.save(worker);
        }
        return new ResponseEntity(workers, HttpStatus.OK);
    }


    @DeleteMapping("/workers/")
    public ResponseEntity deleteAll() {
        repository.deleteAll();
        List<Task> tasks = ControllerHelper.getTasksFromDB(taskRepository);
        tasks.forEach(task -> {
            task.setWorker(null);
            taskRepository.save(task);
        });
        List<Worker> deletedWorkerList = ControllerHelper.getWorkersFromDB(repository);
        if (deletedWorkerList.isEmpty()) {
            return new ResponseEntity("Worker list deleted", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting");
    }

}
