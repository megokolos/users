package ru.kolosov.CRUD.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {

    private Long id;

    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
