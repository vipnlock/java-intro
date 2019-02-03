package ch.learnspace.spring.demo.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "ch.learnspace.spring.demo.data")
public class PersistenceJPAConfig {
    //TODO: Create Config
    //Help: https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
}
