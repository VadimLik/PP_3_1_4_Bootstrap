package ru.kata.spring.boot_security.service;

import ru.kata.spring.boot_security.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    public List<User> getAllUsers();

    public User getUser(long id);

    public void saveUser(User user);

    public void updateUser(User user, long id);

    public void deleteUser(long id);

    public Optional<User> findByUserName(String email);
}
