package main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName (value = "index page check")
    public void getHello() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.ALL))
                    .andExpect(status().isOk())
                    .andExpect(content().string(equalTo("Hello! Current date is " + (new Date().toString()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
