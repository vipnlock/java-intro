package ch.learnspace.spring.demo.integration.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingsControllerIntegrationTest {

    private static final String USERNAME = "test-user-name";

    @LocalServerPort
    private int port;

    private String urlPrefix;

    @Autowired
    private TestRestTemplate restTemplate;

    @PostConstruct
    private void setup() {
        urlPrefix = "http://localhost:" + port;
    }

    @Test
    @DisplayName("Call '/api/greeting/hello'")
    void helloWorld() {
        String result = restTemplate.getForObject(urlPrefix + "/api/greeting/hello", String.class);

        assertEquals("Hello World!", result);
    }

    @Test
    @DisplayName("Call '/api/greeting/{username}'")
    void helloUser() {
        String result = restTemplate.getForObject(urlPrefix + "/api/greeting/" + USERNAME, String.class);

        assertEquals("Hello " + USERNAME + "!", result);
    }

}
