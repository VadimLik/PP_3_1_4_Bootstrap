package ru.kata.spring.boot_security.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty(message = "Email не может быть пустым")
    @Email(message = "Ввод данных не соответствует представлению email")
    @Column(name = "userName")
    private String userName;

    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 3, message = "Пароль содержит не менее трех знаков")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 30, message = "Имя в пределах от 2 до 30 знаков")
    @Column(name = "firstName")
    private String firstName;

    @NotEmpty(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 30, message = "Фамилия в пределах от 2 до 30 знаков")
    @Column(name = "lastName")
    private String lastName;

    @Min(value = 0, message = "Возраст не может быть менее 0")
    @Column(name = "age")
    private byte age;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {

    }

    public User(String username,String password, String firstname, String lastName, byte age, Collection<Role> roles) {

        this.userName = username;

        this.password = password;

        this.firstName = firstname;

        this.lastName = lastName;

        this.age = age;

        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getPassword() { return password; }

    public void setPassword (String password) { this.password = password; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public Collection<Role> getRoles() { return roles; }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "email=" + userName +
                ", firstname='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}
