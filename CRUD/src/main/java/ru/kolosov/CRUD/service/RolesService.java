package ru.kolosov.CRUD.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;

public interface RolesService {
    Role findByRole(String name);

    @Transactional
    void save(Role role);

    @Transactional
    void update(Long id, Role role);

    @Transactional
    void delete (Long id);
}
