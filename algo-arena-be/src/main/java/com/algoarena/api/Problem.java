package com.algoarena.api;

import com.algoarena.dao.repo.ProblemRepo;
import com.algoarena.problems.ProblemDetails;
import com.algoarena.problems.ProblemDetailsService;
import com.algoarena.problems.ProblemListService;
import com.algoarena.problems.ProblemWithStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // For testing in DEV env
public class Problem {

    private ProblemRepo problemRepo;
    private ProblemDetailsService problemDetailsService;
    private ProblemListService problemListService;

    @GetMapping("problems")
    public List<ProblemWithStatus> getProblemWithStatus(){
        return problemListService.getProblemListWithStatus();
    }

    @GetMapping("problems/{problemId}/{languageId}")
    public ResponseEntity<ProblemDetails> getProblem(@PathVariable("problemId") UUID problemId, @PathVariable("languageId") String languageId){
        return ResponseEntity.ok(problemDetailsService.getProblemDetails(problemId,languageId));
    }
}
