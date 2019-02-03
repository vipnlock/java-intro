package ch.learnspace.spring.demo.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
/*
 * Commented @ComponentScan, because is not needed (corresponds the default behaviour) and harms for "sliced tests".
 * see https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-detecting-config
 *
 * Excerpt from the documentation:
 * If you use a test annotation to test a more specific slice of your application,
 * you should avoid adding configuration settings that are specific to a particular
 * area on the main method’s application class.
 */
// @ComponentScan(basePackages = "ch.learnspace.spring.demo.mvc")
public class WebMvcConfig implements WebMvcConfigurer {

}
