package ch.learnspace.spring.demo;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/*
 * We want our modules to me loosely coupled and separately testable.
 * So we create a custom configuration class annotated with @SpringBootConfiguration
 * for each module to take care of stuff that is only relevant within the respective module.
 */
@SpringBootConfiguration
/*
 * When running the application, this is actually not needed,
 * since the @SpringBootApplication already brings it into play.
 *
 * But when we want to start up a single module configuration in a test
 * (without starting the whole ApplicationContext), we need to enable auto-configuration by hand like this.
 */
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = { TypeExcludeFilter.class}
        ), @ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = { AutoConfigurationExcludeFilter.class}
        )})
public class DemoModuleConfiguration {

}
