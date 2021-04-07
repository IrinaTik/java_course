package main;

import BDEmulator.Storage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import response.Task;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

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
                Storage.addTask(task);
            }
            this.base = new URL("http://localhost:" + port + "/tasks/");
            ResponseEntity<Task[]> response = template.getForEntity(base.toString(), Task[].class);
            System.out.println("Status - " + response.getStatusCode());
            for (int i = 0; i < response.getBody().length; i++) {
                Task task = response.getBody()[i];
                System.out.println(task);
                assertThat(task.equals(Storage.getAllTasks().get(i)));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetOneTask() {
        try {
            for (char c = 'a'; c <= 'z'; c++) {
                Task task = new Task();
                task.setWorkerId(1);
                task.setContext(String.valueOf(c));
                Storage.addTask(task);
            }
            this.base = new URL("http://localhost:" + port + "/tasks/3");
            ResponseEntity<Task> response = template.getForEntity(base.toString(), Task.class);
            System.out.println("Status - " + response.getStatusCode());
            System.out.println(response.getBody());
            assertThat(response.getBody().equals(Storage.getAllTasks().get(2)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
