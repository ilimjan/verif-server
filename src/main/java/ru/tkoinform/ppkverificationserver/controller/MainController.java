package ru.tkoinform.ppkverificationserver.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tkoinform.ppkverificationserver.CrossOrigins;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = CrossOrigins.value)
public class MainController {

    public static final String BASE_PATH = "/api";

    @GetMapping
    public String lifeCheck() {
        return "I am alive!";
    }
}
