package ru.kolosov.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.repository.UsersRepository;
import ru.kolosov.CRUD.model.MyUserDetails;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public User findByLogin(String login) {
        User user = usersRepository.findByLogin(login);
        user.getRoles().size();
        return user;
    }

    @Transactional
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Transactional
    public void update(Long id, User updatedPerson) {
        updatedPerson.setId(id);
        usersRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User optionalUser = usersRepository.findByLogin(login);
        if (optionalUser==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new MyUserDetails(optionalUser);
    }
}
