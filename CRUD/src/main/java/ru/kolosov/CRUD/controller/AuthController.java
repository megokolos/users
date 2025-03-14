package ru.kolosov.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "logInOut/login";
    }

    @GetMapping("/logout")
    public String logoutForm() {
        return "logInOut/logout";
    }

    @PostMapping("/logout")
    public String logout() {
        return "logInOut/login";
    }

}
