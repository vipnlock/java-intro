package ch.learnspace.spring.demo.integration.server;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/*
 * Testing with a running server.
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-with-running-server
 *
 * Test that asserts the behaviour of our application.
 * To do that we start the application up and listen for a connection like it would do in production,
 * and then send an HTTP request and assert the response.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerAsServerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void homePageShouldReturnDefaultMessage() throws Exception {
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
        assertThat(result).contains("Hello And Welcome!");
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        String result = this.restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
        assertThat(result).contains("Hello World");
    }

}
