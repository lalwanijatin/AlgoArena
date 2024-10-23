package com.algoarena.api;

import com.algoarena.codesubmit.CodeSubmitter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class Submit {
    private CodeSubmitter codeSubmitter;

    public Submit(CodeSubmitter codeSubmitter){
        this.codeSubmitter = codeSubmitter;
    }

    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submit(@RequestBody Map<String,Object> body) throws IOException {
        String problemName = (String)body.get("problemName");
        String userCode = (String)body.get("userCode");
        String languageId = body.get("languageId").toString();

        UUID submissionId = codeSubmitter.submit(problemName, userCode, languageId);

        Map<String,Object> response = new HashMap<>();
        response.put("submissionId",submissionId);

        return ResponseEntity.ok(response);
    }

}
