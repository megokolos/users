package ru.kolosov.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.repository.RolesRepository;
import ru.kolosov.CRUD.repository.UsersRepository;
import ru.kolosov.CRUD.model.MyUserDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    @Autowired
    private RolesRepository rolesRepository;

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
    public void update(Long id, User updatedUser) {
        User existingUser = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Обновляем поля пользователя
        existingUser.setLogin(updatedUser.getLogin());
        existingUser.setName(updatedUser.getName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());

        // Шифруем пароль, если он был изменен
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }

        // Обновляем роли
        if (updatedUser.getRoles() != null && !updatedUser.getRoles().isEmpty()) {
            Set<Role> updatedRoles = updatedUser.getRoles().stream()
                    .map(role -> rolesRepository.findById(role.getId())
                            .orElseThrow(() -> new RuntimeException("Role not found")))
                    .collect(Collectors.toSet());
            existingUser.setRoles(updatedRoles);
        }

        usersRepository.save(existingUser);
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
