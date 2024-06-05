package ru.kata.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.service.RoleService;
import ru.kata.spring.boot_security.service.RoleServiceImp;
import ru.kata.spring.boot_security.service.UsersService;
import ru.kata.spring.boot_security.validators.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class UsersController {

    private final UsersService usersService;

    private final RoleService roleService;

    private final UserValidator userValidator;


    @Autowired
    public UsersController(UsersService userService, RoleService roleService, UserValidator userValidator) {

        this.usersService = userService;

        this.roleService = roleService;

        this.userValidator = userValidator;

    }

    @GetMapping("/user")
    public String showUserInfo(Model model, Principal principal){

        User user = usersService.findByUserName(principal.getName()).orElseThrow(() -> new RuntimeException("Невозможно найти пользователя по username: " + principal.getName()));

        model.addAttribute("user", user);

        return "userPage";
    }

    @GetMapping("/admin")
    public String getUsers( Model model, Principal principal) {

        System.out.println("Начало работы метода getUsers, GET запрос");

        User user = usersService.findByUserName(principal.getName()).orElseThrow(() -> new RuntimeException("Невозможно найти пользователя по username: " + principal.getName()));

        model.addAttribute("activeTab", "usersTable");

        model.addAttribute("newUser", new User());

        model.addAttribute("user", user);

        model.addAttribute("userlist", usersService.getAllUsers());

        System.out.println("Конец работы метода getUsers, GET запрос");

        return "adminPage";
    }


    @PostMapping("/admin/new")
    public String createUser(@ModelAttribute("newUser") @Valid  User user, BindingResult bindingResult) {

        System.out.println("Начало работы метода createUser, POST запрос");

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){

            System.out.println("Конец работы метода createUser, в bindingResult содержится ошибка,  POST запрос");

            return "redirect:/admin";
        }

        usersService.saveUser(user);

        System.out.println("Конец работы метода createUser, POST запрос");

        return "redirect:/admin";

    }

    @DeleteMapping("/admin")
    public String deleteUser(@RequestParam("id") long id) {

        usersService.deleteUser(id);

        return "redirect:/admin";
    }

    @PatchMapping("/admin")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             Model model, Principal principal,
                             @RequestParam("id") long id) {

        System.out.println("Начало работы метода updateUser, PATCH запрос");

        model.addAttribute("user", usersService.findByUserName(principal.getName()).orElse(null));

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()){

            System.out.println("Конец работы метода updateUser, обнаружена ошибка,  PATCH запрос");

            return "redirect:/admin";
        }

        usersService.updateUser(user, id);

        System.out.println("Конец работы метода updateUser, PATCH запрос");

        return "redirect:/admin";
    }

    @ModelAttribute("roles")
    public Collection<Role> getCollectionRole(Model model) {

        Collection<Role> roles = roleService.getAllRoles();

        return roles;
    }

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }
}
