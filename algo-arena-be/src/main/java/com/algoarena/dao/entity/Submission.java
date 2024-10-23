package com.algoarena.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "Submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "problem_id", nullable = false)
    private UUID problemId;

    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @Column(name = "code", nullable = false, columnDefinition = "TEXT")
    private String code;

    @Column(name = "active_contest_id")
    private UUID activeContestId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "PENDING";

    @Column(name = "memory")
    private Integer memory;

    @Column(name = "time")
    private Float time;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Problem problem;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "submissionid", referencedColumnName = "id", insertable = false, updatable = false)
    private List<Submissions> submissions;
}
