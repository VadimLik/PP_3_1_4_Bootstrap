package ru.kata.spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService{

//    public final static Collection<Role> list = new ArrayList<>();
//
//    {
//        list.add(new Role("ROLE_ADMIN"));
//
//        list.add(new Role("ROLE_USER"));
//    }

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Collection<Role> getAllRoles() {
        return (Collection<Role>) roleRepository.findAll();
    }

    public void save(Role roles){ roleRepository.save(roles);}
}
