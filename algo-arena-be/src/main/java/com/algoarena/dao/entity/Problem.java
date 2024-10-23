package com.algoarena.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "Problem")
public class Problem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "hidden", nullable = false)
    private boolean hidden = true;

    @Column(name = "solved_count", nullable = false)
    private int solvedCount = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt = new Date();

    @Column(name = "difficulty", length = 10, nullable = false)
    private String difficulty = "EASY";

    @Column(name = "acceptance", nullable = false)
    private int acceptance = 0;
}

