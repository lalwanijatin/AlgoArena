package com.algoarena.api;

import com.algoarena.boilerplate.CodeGenerator;
import com.algoarena.dao.entity.Problem;
import com.algoarena.dao.repo.ProblemRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BoilerplateCode {

    private CodeGenerator codeGenerator;
    private ProblemRepo problemRepo;

    @PostMapping("/generateBoilerplate/{problemName}/{difficulty}")
    public void generateBoilerplate(@PathVariable String problemName, @PathVariable String difficulty) throws IOException {
        codeGenerator.generate(problemName);

        Problem problem = new Problem();
        problem.setName(problemName);
        problem.setDifficulty(difficulty);
        problemRepo.save(problem);
    }

    @PutMapping("/updateBoilerplate/{problemName}")
    public void updateBoilerplate(@PathVariable String problemName) throws IOException {
        codeGenerator.generate(problemName);
    }
}
