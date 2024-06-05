package ru.kata.spring.boot_security.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
