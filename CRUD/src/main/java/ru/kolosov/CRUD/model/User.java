package ru.kolosov.CRUD.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @Column(name = "lastname")
    private String lastName;
    @NonNull
    private Integer age;
    @NonNull
    private String email;
    @NonNull
    private String login;
    @NonNull
    private String password;
}
