package ru.kolosov.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.service.RolesService;
import ru.kolosov.CRUD.service.UsersService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("/admin/users")
    public String showAll(Model model) {
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("allRoles", rolesService.findAll());
        return "users/index";
    }

    @GetMapping("/users/{id}")
    public String show() {
        return "users/show";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/users/new")
    public String create(@ModelAttribute("user") User user) {
        Role userRole = rolesService.findByRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            rolesService.save(userRole);
        }
        user.getRoles().add(userRole);
        usersService.save(user);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/users/new/admin")
    public String newAdmin(@ModelAttribute("user") User user) {
        return "users/newAdmin";
    }

    @PostMapping("/users/new/admin")
    public String createAdmin(@ModelAttribute("user") User user) {
        Role userRoleAdmin = rolesService.findByRole("ROLE_ADMIN");
        if (userRoleAdmin == null) {
            userRoleAdmin = new Role("ROLE_ADMIN");
            rolesService.save(userRoleAdmin);
        }
        Role userRoleUser = rolesService.findByRole("ROLE_USER");
        if (userRoleUser == null) {
            userRoleUser = new Role("ROLE_USER");
            rolesService.save(userRoleUser);
        }
        user.getRoles().add(userRoleAdmin);
        user.getRoles().add(userRoleUser);
        usersService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", usersService.findById(id));
        model.addAttribute("allRoles", rolesService.findAll());
        return "users/edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        usersService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String delete(@PathVariable("id") Long id) {
        usersService.delete(id);
        return "redirect:/admin/users";
    }
}
