package main;

import main.BDEmulator.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import main.response.Task;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    private final int taskIndex = 3; // номер записи для экзекуций, выбранный левой пяткой

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Storage storage;

    @BeforeEach
    public void fillStorage() {
        for (char c = 'a'; c <= 'z'; c++) {
            Task task = new Task();
            task.setWorkerId(1);
            task.setContext(String.valueOf(c));
            storage.addTask(task);
        }
    }

    @AfterEach
    public void clearStorage() {
        storage.getAllTasks().clear();
    }

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
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
            List<Future> futures = new ArrayList<>();
            this.base = new URL("http://localhost:" + port + "/tasks/" + taskIndex);
            Runnable task = () -> {
                ResponseEntity<Task> response = template.getForEntity(base.toString(), Task.class);
                System.out.println("Status - " + response.getStatusCode());
                System.out.println(response.getBody());
                assertThat(response.getBody().equals(storage.getAllTasks().get(taskIndex - 1)));
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
        try {
            this.base = new URL("http://localhost:" + port + "/tasks/" + taskIndex);
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
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<Future> futures = new ArrayList<>();
        // удаляем несколько записей подряд из рандомного места списка
        for (int i = 3; i < 13; i++) {
            String url = "http://localhost:" + port + "/tasks/" + i;
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

        for (int i = 3; i < 13; i++) {
            assertThat(storage.getTask(i) == null);
        }
        System.out.println(storage.getAllTasks());
    }

    // удаление всех заданий одним запросом
    @Test
    public void deleteAllTasks() {
        try {
            this.base = new URL("http://localhost:" + port + "/tasks/");
            template.delete(base.toString());
            assertTrue(storage.getAllTasks().isEmpty());
            System.out.println(storage.getAllTasks());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // изменение одной записи через PUT
    @Test
    public void changeOneTask() {
        try {
            Task updatedTaskInfo = new Task();
            updatedTaskInfo.setId(taskIndex);
            updatedTaskInfo.setContext("qwerty");
            updatedTaskInfo.setWorkerId(13);
            this.base = new URL("http://localhost:" + port + "/tasks/" + taskIndex);
            template.put(base.toString(), updatedTaskInfo);
            assertThat(storage.getTask(taskIndex).equals(updatedTaskInfo));
            System.out.println(storage.getAllTasks());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
