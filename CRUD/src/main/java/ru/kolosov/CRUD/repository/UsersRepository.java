package ru.kolosov.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolosov.CRUD.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
}
