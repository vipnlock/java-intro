package ch.learnspace.spring.demo.integration.mockmvc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.learnspace.spring.demo.data.entity.City;
import ch.learnspace.spring.demo.mvc.rest.controller.CityController;
import ch.learnspace.spring.demo.service.CityService;
import ch.learnspace.spring.demo.setup.helper.TestObjectCreator;

/*
 * === 2. Integration Testing the Web Layer with @WebMvcTest
 *
 * @WebMvcTest sets up an application context with everything we need for testing a Spring MVC controller.
 * @WebMvcTest goes up the package structure to the first @SpringBootConfiguration it finds and uses it
 * as the root for the application context.
 *
 * Thanks to our module configurations, @WebMvcTest will only load the application context needed for the module we’re currently testing.
 *
 * see:
 * https://reflectoring.io/testing-verticals-and-layers-spring-boot/
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
 */
@WebMvcTest(value = CityController.class)
public class CityControllerWebMvcTest {

    /*
     * Among other things, @WebMvcTest provides a MockMvc instance to the application context.
     * We can simply inject it and use it to simulate HTTP calls against our REST controller and assert the results.
     */
    @Autowired
    private MockMvc mockMvc;

    /*
     * We use @MockBean to replace the real instance of BookingService with a Mockito mock object.
     * In the test, we tell the mock object what to return to satisfy our test setup.
     */
    @MockBean
    private CityService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        final City testCity = TestObjectCreator.createRandomCity();

        Mockito.when(service.getCity(1L))
               .thenReturn(testCity);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/world/city/1"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(testCity.getName())));
    }

}
