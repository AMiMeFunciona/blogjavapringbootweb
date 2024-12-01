package com.principal.pruebaspringbootweb.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tb_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Long id;

    @Column(name = "rol_name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
