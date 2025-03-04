package ru.kolosov.CRUD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolosov.CRUD.model.Role;
import ru.kolosov.CRUD.model.User;
import ru.kolosov.CRUD.repository.RolesRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RolesServiceImp implements RolesService{

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    public Role findByRole(String name) {
        return rolesRepository.findByRole(name);
    }

    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Override
    public void update(Long id, Role role) {
        role.setId(id);
        rolesRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        rolesRepository.deleteById(id);
    }
}
