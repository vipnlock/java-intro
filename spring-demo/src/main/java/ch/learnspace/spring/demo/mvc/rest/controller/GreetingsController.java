package ch.learnspace.spring.demo.mvc.rest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/greeting")
public class GreetingsController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/{user}")
    public String helloUser(@PathVariable String user) {
        return "Hello " + user + "!";
    }
}
