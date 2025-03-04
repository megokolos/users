package ru.kolosov.CRUD.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.User;

import java.util.List;


public interface UsersService extends UserDetailsService{

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    void save(User user);

    @Transactional
    void update(Long id, User updatedPerson);

    @Transactional
    void delete (Long id);
}
