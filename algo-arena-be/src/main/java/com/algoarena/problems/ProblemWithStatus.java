package com.algoarena.problems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemWithStatus {
    private UUID id;
    private String name;
    private String difficulty = "EASY";
    private int acceptance = 0;
    private boolean solvedByCurrentUser = false;
}
