package ru.kolosov.CRUD.controller.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolosov.CRUD.dto.UsersDTO;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.service.RolesService;
import ru.kolosov.CRUD.service.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersRESTControllers {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsersDTO() {
        return ResponseEntity.status(HttpStatus.OK).body(usersService.findAll().stream()
                .map(UsersDTO::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        
        Role userRole = rolesService.findByRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            rolesService.save(userRole);
        }
        user.getRoles().add(userRole);
        usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody User user) {
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
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody User user,
                         @PathVariable("id") Long id) {
        usersService.update(id, user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        usersService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = usersService.findById(id);
        return ResponseEntity.ok(user);
    }
}
