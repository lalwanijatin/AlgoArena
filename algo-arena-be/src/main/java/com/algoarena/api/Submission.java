package com.algoarena.api;

import com.algoarena.dao.repo.SubmissionRepo;
import com.algoarena.usersubmissions.SubmissionsDTO;
import com.algoarena.usersubmissions.SubmissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // For testing in DEV env
public class Submission {

    @Autowired
    private SubmissionRepo submissionRepo;

    @Autowired
    private SubmissionsService submissionsService;

    @GetMapping("/checkstatus/{submissionId}")
    public com.algoarena.dao.entity.Submission getSubmissionById(@PathVariable("submissionId") UUID submissionId){
        com.algoarena.dao.entity.Submission submission = submissionRepo.findById(submissionId).get();
        return submission;
    }

    @GetMapping("/latestsubmissions/{problemId}")
    public List<com.algoarena.dao.entity.Submission> getSubmissionsByProblemId(@PathVariable("problemId") UUID problemId){
        return submissionRepo.findTop10ByProblemIdOrderByCreatedAtDesc(problemId);
    }

    @GetMapping("/submissions/{username}")
    public List<SubmissionsDTO> getSubmissionsByUsername(@PathVariable("username") String username){
        return submissionsService.getSubmissionsDTO(username);
    }
}
