package ru.kolosov.CRUD.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.kolosov.CRUD.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class UsersDTO {

    private Long id;
    @NonNull
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    private String name;

    @NonNull
    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Column(name = "lastname")
    private String lastName;

    @NonNull
    @Min(value = 0, message = "Age cant be less then 0")
    @Max(value = 150, message = "Age cant be greater then 150")
    private Integer age;

    @NonNull
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format. Example: example@mail.com")
    private String email;

    @NonNull
    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 2, max = 20, message = "Логин должен быть от 2 до 20 символов длиной")
    private String login;

    private String password;

    private List<RolesDTO> roles = new ArrayList<>();


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
