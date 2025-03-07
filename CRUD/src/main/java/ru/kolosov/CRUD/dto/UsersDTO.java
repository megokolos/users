package ru.kolosov.CRUD.dto;

import lombok.Data;
import ru.kolosov.CRUD.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsersDTO {

    private Long id;
    private String name;
    private String lastName;
    private int age;
    private String email;
    private String login;
    private List<RolesDTO> roles;

    public UsersDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.roles = user.getRoles().stream()
                .map(RolesDTO::new)
                .collect(Collectors.toList());
    }
}
