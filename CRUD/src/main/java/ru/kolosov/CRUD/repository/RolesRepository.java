package ru.kolosov.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolosov.CRUD.model.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
