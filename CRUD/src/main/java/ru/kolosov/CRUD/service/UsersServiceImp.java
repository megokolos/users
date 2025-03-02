package ru.kolosov.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.repository.UsersRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService{
    @Autowired
    private UsersRepository usersRepository;

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(Long id, User updatedPerson) {
        updatedPerson.setId(id);
        usersRepository.save(updatedPerson);
    }

    @Transactional
    public void delete (Long id) {
        usersRepository.deleteById(id);
    }
}
