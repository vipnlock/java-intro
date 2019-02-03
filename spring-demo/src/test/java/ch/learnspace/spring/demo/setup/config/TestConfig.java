package ch.learnspace.spring.demo.setup.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(value = "classpath:test.properties")
public class TestConfig {

    @Value("user.name.one")
    private String myValue;

}
