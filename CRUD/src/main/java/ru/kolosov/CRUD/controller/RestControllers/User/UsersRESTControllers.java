package ru.kolosov.CRUD.controller.RestControllers.User;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kolosov.CRUD.dto.RolesDTO;
import ru.kolosov.CRUD.dto.UsersDTO;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.service.crud.RolesService;
import ru.kolosov.CRUD.service.crud.UsersService;

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
    public ResponseEntity<String> createUser(@RequestBody @Valid UsersDTO usersDTO,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    System.out.println("Validation error: " + error.getField() + " - " + error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка создания");
        }

        Role userRole = rolesService.findByRole("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            rolesService.save(userRole);
        }
        User user = convertToUser(usersDTO);
        user.getRoles().add(userRole);
        usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }


    @PostMapping("/createAdmin")
    public ResponseEntity<String> createAdmin(@RequestBody UsersDTO usersDTO,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error ->
                    System.out.println("Validation error: " + error.getField() + " - " + error.getDefaultMessage())
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка создания");
        }

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
        User user = convertToUser(usersDTO);
        user.getRoles().add(userRoleAdmin);
        user.getRoles().add(userRoleUser);
        usersService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin created successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid UsersDTO usersDTO,
                                         @PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("ID пользователя не может быть null");
        }
        User user = convertToUser(usersDTO);
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

    @GetMapping("/csrf")
    public CsrfToken getCsrfToken(CsrfToken csrfToken) {
        return csrfToken;
    }

    private User convertToUser(UsersDTO usersDTO) {
        User user = new User();
        user.setName(usersDTO.getName());
        user.setLastName(usersDTO.getLastName());
        user.setAge(usersDTO.getAge());
        user.setEmail(usersDTO.getEmail());
        user.setLogin(usersDTO.getLogin());
        user.setPassword(usersDTO.getPassword());
        if (user.getRoles() != null) {
            for (RolesDTO rolesDTO : usersDTO.getRoles()) {
                user.getRoles().add(new Role(rolesDTO.getRole()));
            }
        }
        return user;
    }
}
