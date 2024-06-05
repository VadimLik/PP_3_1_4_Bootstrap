package ru.kata.spring.boot_security.service;

import ru.kata.spring.boot_security.models.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    Collection<Role> getAllRoles();
}
