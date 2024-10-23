package com.algoarena.usersubmissions;

import com.algoarena.dao.repo.SubmissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionsService {
    @Autowired
    private SubmissionRepo submissionRepo;

    public List<SubmissionsDTO> getSubmissionsDTO(String username){
        return submissionRepo.findSubmissionsDTOByUsername(username);
    }
}
