package ru.kata.spring.boot_security.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.repositories.RoleRepository;
import ru.kata.spring.boot_security.repositories.UserRepository;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.RoleServiceImp;
import ru.kata.spring.boot_security.service.UsersService;
import ru.kata.spring.boot_security.service.UsersServiceImp;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;


@Component
public class DataBaseInit {
    private final UsersServiceImp usersService;

    private final RoleServiceImp roleService;

    @Autowired
    public DataBaseInit(UsersServiceImp usersService, RoleServiceImp roleService) {

        this.usersService = usersService;

        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {

        Role adminRole = new Role("ROLE_ADMIN");

        Role userRole = new Role("ROLE_USER");

        Collection<Role> rolesAdmin = new ArrayList<>();

        rolesAdmin.add(adminRole);

        rolesAdmin.add(userRole);

        Collection<Role> rolesUser = new ArrayList<>();

        rolesUser.add(userRole);

        if(roleService.getAllRoles().isEmpty()) {

            roleService.save(adminRole);

            roleService.save(userRole);

        }

        User admin = new User(
                "admin@mail.ru",
                "$2a$12$Snsakf1bFnPlZBqGtKRv/.n9uW4RXvAfR4ZJxsJvxz6r/WgAEBko2",
                "Vadim",
                "Lik",
                (byte) 54,
                rolesAdmin);

        User user = new User(
                "user@mail.ru",
                "$$2a$10$4qA4/3I6f/uY8wW8m4AzeOmPiwy5W5e0KaFwQA3Ym2cLm.lgBNJAK",
                "Ivan",
                "Ivanov",
                (byte) 20,
                rolesUser);

        User loadUser = usersService.findByUserName(admin.getUserName()).orElse(null);

        if(loadUser == null) {

            usersService.save(admin);

        }

        loadUser = usersService.findByUserName(user.getUserName()).orElse(null);

        if(loadUser == null) {

            usersService.save(user);

        }

    }
}
