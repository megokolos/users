package ru.kolosov.CRUD.service.crud;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.Role;

import java.util.List;


public interface RolesService {
    Role findByRole(String name);

    List<Role> findAll();

    @Transactional
    void save(Role role);

    @Transactional
    void update(Long id, Role role);

    @Transactional
    void delete(Long id);
}
