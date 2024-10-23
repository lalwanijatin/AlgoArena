package com.algoarena.problems;

import com.algoarena.dao.entity.Problem;
import com.algoarena.dao.repo.ProblemRepo;
import com.algoarena.dao.repo.SubmissionRepo;
import com.algoarena.util.UserDetailsGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemListService {
    @Autowired
    private ProblemRepo problemRepo;
    @Autowired
    private SubmissionRepo submissionRepo;
    @Autowired
    private UserDetailsGetter userDetailsGetter;

    public List<ProblemWithStatus> getProblemListWithStatus(){
        String username = userDetailsGetter.getUserName();
        List<ProblemWithStatus> problemWithStatusList = new ArrayList<>();

        Iterable<Problem> problemsList = problemRepo.findAll();
        if(username == null){
            problemsList.forEach(problem -> problemWithStatusList.add(new ProblemWithStatus(problem.getId(),problem.getName(),problem.getDifficulty(), problem.getAcceptance(),false)));
        }else{
            List<UUID> solvedProblemIds = submissionRepo.findProblemIdByUsername(username);
            Set<UUID> setOfSolvedProblemIds = new HashSet<>(solvedProblemIds);
            problemsList.forEach(problem -> problemWithStatusList.add(new ProblemWithStatus(problem.getId(),problem.getName(),problem.getDifficulty(), problem.getAcceptance(),setOfSolvedProblemIds.contains(problem.getId()))));
        }

        return problemWithStatusList;
    }
}
