package ru.kolosov.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.service.UsersService;


@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersService.findById(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        user.setRole("ROLE_USER");
        usersService.save(user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/new/admin")
    public String newAdmin(@ModelAttribute("user") User user) {
        return "users/newAdmin";
    }

    @PostMapping("/new/admin")
    public String createAdmin(@ModelAttribute("user") User user) {
        user.setRole("ROLE_ADMIN");
        usersService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", usersService.findById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        usersService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        usersService.delete(id);
        return "redirect:/users";
    }
}
