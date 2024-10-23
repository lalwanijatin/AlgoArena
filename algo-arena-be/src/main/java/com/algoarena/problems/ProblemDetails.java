package com.algoarena.problems;

import com.algoarena.dao.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetails {
    private Problem problem;
    private String initialCode;
    private String description;
}
