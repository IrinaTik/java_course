package main;

import BDEmulator.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import response.Task;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Storage storage;

    @Test
    public void scanContextForStorageComponent() {
        assertNotNull(applicationContext.getBean(Storage.class));
    }

    @Test
    public void testAddTask() {
        try {
            this.base = new URL("http://localhost:" + port + "/tasks/");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            for (char c = 'a'; c <= 'z'; c++) {
                Task task = new Task();
                task.setWorkerId(1);
                task.setContext(String.valueOf(c));
                HttpEntity<Task> taskEntity = new HttpEntity<>(task, headers);
                ResponseEntity<Integer> response = template.postForEntity(base.toString(), taskEntity, Integer.class);
                assertThat(response.getBody().equals(task.getId()));
            }
            System.out.println(storage.getAllTasks());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetAllTasks() {
        try {
            for (char c = 'a'; c <= 'z'; c++) {
                Task task = new Task();
                task.setWorkerId(1);
                task.setContext(String.valueOf(c));
                storage.addTask(task);
            }
            this.base = new URL("http://localhost:" + port + "/tasks/");
            ResponseEntity<Task[]> response = template.getForEntity(base.toString(), Task[].class);
            System.out.println("Status - " + response.getStatusCode());
            for (int i = 0; i < response.getBody().length; i++) {
                Task task = response.getBody()[i];
                System.out.println(task);
                assertThat(task.equals(storage.getAllTasks().get(i)));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // запрос GET с имитацией многопоточности
    @Test
    public void testGetOneTask() {
        try {
            for (char c = 'a'; c <= 'z'; c++) {
                Task task = new Task();
                task.setWorkerId(1);
                task.setContext(String.valueOf(c));
                storage.addTask(task);
            }
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
            List<Future> futures = new ArrayList<>();
            this.base = new URL("http://localhost:" + port + "/tasks/3");
            Runnable task = () -> {
                ResponseEntity<Task> response = template.getForEntity(base.toString(), Task.class);
                System.out.println("Status - " + response.getStatusCode());
                System.out.println(response.getBody());
                assertThat(response.getBody().equals(storage.getAllTasks().get(2)));
            };
            for (int i = 0; i < 10; i++) {
                futures.add(executor.submit(task));
            }
            futures.forEach(res -> {
                try {
                    res.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteOneTask() {
        for (char c = 'a'; c <= 'z'; c++) {
            Task task = new Task();
            task.setWorkerId(1);
            task.setContext(String.valueOf(c));
            storage.addTask(task);
        }
        try {
            this.base = new URL("http://localhost:" + port + "/tasks/3");
            template.delete(base.toString());
            ResponseEntity<Task> response = template.getForEntity(base.toString(), Task.class);
            System.out.println(response.getStatusCode());
            assertThat(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
            System.out.println(storage.getAllTasks());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // удаление с имитацией многопоточности
    @Test
    public void deleteSeveralTasks() {
        for (char c = 'a'; c <= 'z'; c++) {
            Task task = new Task();
            task.setWorkerId(1);
            task.setContext(String.valueOf(c));
            storage.addTask(task);
        }

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String url = "http://localhost:" + port + "/tasks/" + (i + 3);
            Runnable runnable = () -> {
                template.delete(url);
            };
            futures.add(executor.submit(runnable));
        }
        futures.forEach(res -> {
            try {
                res.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println(storage.getAllTasks());
    }

    // удаление всех заданий одним запросом
    @Test
    public void deleteAllTasks() {
        for (char c = 'a'; c <= 'z'; c++) {
            Task task = new Task();
            task.setWorkerId(1);
            task.setContext(String.valueOf(c));
            storage.addTask(task);
        }
        try {
            this.base = new URL("http://localhost:" + port + "/tasks/");
            template.delete(base.toString());
            System.out.println(storage.getAllTasks());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
