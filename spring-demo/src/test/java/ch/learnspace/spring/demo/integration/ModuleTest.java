package ch.learnspace.spring.demo.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch.learnspace.spring.demo.mvc.web.HomeController;

/*
 * Sanity test.
 * see https://spring.io/guides/gs/testing-web/
 *
 * The @SpringBootTest annotation tells Spring Boot to go and look for a main configuration class
 * (one with @SpringBootApplication for instance), and use that to start a Spring application context.
 */
@SpringBootTest
public class ModuleTest {

    /*
     * The @Autowired annotation is interpreted by the Spring and the controller is injected before the test methods are run.
     */
    @Autowired
    private HomeController controller;

    /*
     * Sanity check: We are using AssertJ (assertThat() etc.) to express the test assertions.
     */
    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
