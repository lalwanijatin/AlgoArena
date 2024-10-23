package com.algoarena.api;

import com.algoarena.dao.repo.SubmissionRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class Status {
    @Autowired
    private SubmissionRepo submissionRepo;

    @GetMapping("/status/{id}")
    public ResponseEntity<Map<String,String>> getCurrentStatus(@PathVariable("id") UUID id){
        return ResponseEntity.ok(Collections.singletonMap("status", submissionRepo.findStatusById(id)));
    }
}
