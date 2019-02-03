package ch.learnspace.spring.demo.config;

import ch.learnspace.spring.demo.data.entity.City;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Aspect
@Configuration
public class AspectConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @AfterReturning(value = "execution(* ch.learnspace.spring.demo.service.CityService.getCity(Long))", returning = "city")
    public void manipulate_CityService_getCity_AfterReturning(JoinPoint joinPoint, City city) {

        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.toString(joinPoint.getArgs());

        logger.info("{} was called with input arguments {}", methodName, arguments);

        // The manipulation is very simple: the names are changed to uppercase
        city.setName(city.getName().toUpperCase());

        logger.info("Finished manipulation for {}", methodName);
    }

    @Before(value = "execution(* ch.learnspace.spring.demo.service.CityService.getRandomCity())")
    public void log_CityService_getRandomCity_Before(JoinPoint joinPoint) {
        logger.info("This message is called before {} was executed.", joinPoint.getSignature().getName());
    }

    @After(value = "execution(* ch.learnspace.spring.demo.service.CityService.getRandomCity())")
    public void log_CityService_getRandomCity_After(JoinPoint joinPoint) {
        logger.info("This message is called after {} was executed.", joinPoint.getSignature().getName());
    }

    @Around(value = "execution(* ch.learnspace.spring.demo.service.CityService.getDummyCity())")
    public City manipulate_CityService_getDummyCity_Around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("This message is called before {} was executed.", joinPoint.getSignature().getName());
        City city = (City) joinPoint.proceed();
        city.setCountrycode("Wonderland");
        logger.info("This message is called after {} was executed.", joinPoint.getSignature().getName());
        return city;
    }

    @AfterThrowing(value = "execution(* ch.learnspace.spring.demo.service.CityService.getExceptionCity())", throwing = "throwable")
    public void log_CityService_getExceptionCity_AfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        logger.warn("Problem occured! Message = {}", throwable.getMessage());
        String methodName = joinPoint.getSignature().getName();
        logger.warn("{} has thrown an exception! Implement the method to avoid.", methodName);
    }


}
