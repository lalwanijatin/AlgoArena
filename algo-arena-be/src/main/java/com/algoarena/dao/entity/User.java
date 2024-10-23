package com.algoarena.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 68, nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}

