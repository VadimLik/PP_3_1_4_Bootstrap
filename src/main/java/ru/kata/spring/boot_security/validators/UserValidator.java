package ru.kata.spring.boot_security.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.models.User;
import ru.kata.spring.boot_security.service.UsersService;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Optional;
import java.util.Set;

@Component
public class UserValidator implements Validator {

    private final UsersService userService;

    @Autowired
    public UserValidator(UsersService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        Optional<User> optionalPerson = userService.findByUserName(user.getUserName());

        if (optionalPerson.isPresent() && optionalPerson.get().getId() != user.getId()) {
            errors.rejectValue("userName", "", "Пользователь с таким userName уже существует");
        }
    }
}
