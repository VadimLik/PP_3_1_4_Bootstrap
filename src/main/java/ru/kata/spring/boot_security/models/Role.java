package ru.kata.spring.boot_security.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Role() {

    }

    public Role(String name) {

        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        switch (this.name){
            case "ROLE_ADMIN" :
                return "ADMIN";
            case "ROLE_USER" :
                return "USER";
            default: return "UNKNOWN";
        }
//        return "Role{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                '}';
    }
}
