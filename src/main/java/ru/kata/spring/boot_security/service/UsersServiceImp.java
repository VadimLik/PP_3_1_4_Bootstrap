package ru.kata.spring.boot_security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.models.Role;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UsersServiceImp implements UsersService, UserDetailsService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;

    @Autowired
    public UsersServiceImp(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    @Override
    public List<User> getAllUsers() {

        return (List<User>) userRepository.findAll();

    }

    @Override
    public User getUser(long id) {

        return userRepository.findById(id).orElse(null);

    }

    @Transactional
    public void saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    @Transactional
    public void save(User user) {

        userRepository.save(user);

    }

    @Transactional
    @Override
    public void updateUser(User user, long id) {

        User updateUser = userRepository.findById(id).orElse(null);

        if(updateUser != null) {

            updateUser.setFirstName(user.getFirstName());
            updateUser.setLastName(user.getLastName());
            updateUser.setAge(user.getAge());
            updateUser.setUserName(user.getUserName());
            updateUser.setRoles(user.getRoles());
            if (!updateUser.getPassword().equals(user.getPassword())) {
                updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

        }

    }


    @Transactional
    @Override
    public void deleteUser(long id) {

        userRepository.deleteById(id);

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
