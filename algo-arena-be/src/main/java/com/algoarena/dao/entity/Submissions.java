package com.algoarena.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "submissions")
public class Submissions {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "submissions_id_seq")
    @SequenceGenerator(name = "submissions_id_seq", sequenceName = "submissions_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "source_code", columnDefinition = "TEXT")
    private String sourceCode;

    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "stdin", columnDefinition = "TEXT")
    private String stdin;

    @Column(name = "expected_output", columnDefinition = "TEXT")
    private String expectedOutput;

    @Column(name = "stdout", columnDefinition = "TEXT")
    private String stdout;

    @Column(name = "status_id")
    private Integer statusId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "finished_at")
    private Date finishedAt;

    @Column(name = "time")
    private BigDecimal time;

    @Column(name = "memory")
    private Integer memory;

    @Column(name = "stderr", columnDefinition = "TEXT")
    private String stderr;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "number_of_runs")
    private Integer numberOfRuns;

    @Column(name = "cpu_time_limit")
    private BigDecimal cpuTimeLimit;

    @Column(name = "cpu_extra_time")
    private BigDecimal cpuExtraTime;

    @Column(name = "wall_time_limit")
    private BigDecimal wallTimeLimit;

    @Column(name = "memory_limit")
    private Integer memoryLimit;

    @Column(name = "stack_limit")
    private Integer stackLimit;

    @Column(name = "max_processes_and_or_threads")
    private Integer maxProcessesAndOrThreads;

    @Column(name = "enable_per_process_and_thread_time_limit")
    private Boolean enablePerProcessAndThreadTimeLimit;

    @Column(name = "enable_per_process_and_thread_memory_limit")
    private Boolean enablePerProcessAndThreadMemoryLimit;

    @Column(name = "max_file_size")
    private Integer maxFileSize;

    @Column(name = "compile_output", columnDefinition = "TEXT")
    private String compileOutput;

    @Column(name = "exit_code")
    private Integer exitCode;

    @Column(name = "exit_signal")
    private Integer exitSignal;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "wall_time")
    private BigDecimal wallTime;

    @Column(name = "compiler_options", length = 255)
    private String compilerOptions;

    @Column(name = "command_line_arguments", length = 255)
    private String commandLineArguments;

    @Column(name = "redirect_stderr_to_stdout")
    private Boolean redirectStderrToStdout;

    @Column(name = "callback_url", length = 255)
    private String callbackUrl;

    @Column(name = "additional_files")
    private byte[] additionalFiles;

    @Column(name = "enable_network")
    private Boolean enableNetwork;

    @Column(name = "submissionid")
    private UUID submissionId;

    @ManyToOne
    @Transient
    @JoinColumn(name = "submissionid", referencedColumnName = "id", insertable = false, updatable = false)
    private Submission submission;
}
