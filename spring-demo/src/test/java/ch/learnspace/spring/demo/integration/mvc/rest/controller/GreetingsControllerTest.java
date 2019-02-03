package ch.learnspace.spring.demo.integration.mvc.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingsControllerTest {

    private static final String USERNAME = "USERNAME";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Hello  World")
    void helloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/greeting/hello"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(content().string(Matchers.is("Hello World!")));
    }

    @Test
    @DisplayName("Hello User")
    void helloUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/greeting/" + USERNAME))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(content().string(Matchers.is("Hello " + USERNAME + "!")));
    }

}