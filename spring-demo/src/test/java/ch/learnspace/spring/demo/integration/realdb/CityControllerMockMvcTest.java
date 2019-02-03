package ch.learnspace.spring.demo.integration.realdb;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*
 * Testing with a mock environment.
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-with-mock-environment
 * see also https://spring.io/guides/gs/testing-web/
 *
 * An useful approach is to not start the server at all, but test only the layer below that,
 * where Spring handles the incoming HTTP request and hands it off to the controller.
 *
 * That way, almost the full stack is used, and the code will be called exactly the same way as if
 * it was processing a real HTTP request, but without the cost of starting the server.
 *
 * To do that we will use Spring’s MockMvc, and we can ask for that to be injected for us by using
 * the @AutoConfigureMockMvc annotation on the test case.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("postgres")
public class CityControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/world/city/1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("KABUL")));
    }

}
