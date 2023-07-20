package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class infoController {

    public infoController(@Value("${server.port}") int port) {
        this.port = port;
    }

    private final int port;

    @GetMapping("/getPort")
    public int getPort() {
        return port;

    }
}
