package ru.kolosov.CRUD.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.User;

import java.util.List;

public interface UsersService {

    List<User> findAll();

    User findById(Long id);

    @Transactional
    void save(User user);

    @Transactional
    void update(Long id, User updatedPerson);

    @Transactional
    void delete (Long id);
}
