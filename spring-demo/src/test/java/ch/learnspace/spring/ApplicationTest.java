package ch.learnspace.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * === 3. Testing ApplicationContext Startup with @SpringBootTest.
 *
 * A must-have test for each Spring Boot application is loading the whole ApplicationContext once
 * to check if the dependencies between the beans are satisfied.
 *
 * Simply use the @SpringBootTest annotation which will automatically search the package structure
 * in and above the current package for a class annotated with @SpringBootConfiguration.
 * Since every @SpringBootApplication is also a @SpringBootConfiguration,
 * it will find our application class in the same package and start the whole application context.
 * If the application context cannot be started due to any configuration error or unsatisfied bean
 * dependencies, the test will fail.
 */
@SpringBootTest
class ApplicationTest {

    @Test
    void applicationContextLoads() {
        // nothing
    }

}