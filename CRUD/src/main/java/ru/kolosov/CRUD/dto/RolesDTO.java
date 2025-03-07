package ru.kolosov.CRUD.dto;

import lombok.Data;
import ru.kolosov.CRUD.model.Role;

@Data
public class RolesDTO {

    private Long id;
    private String role;

    public RolesDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
    }
}