package com.algoarena.problems;

import com.algoarena.dao.entity.Problem;
import com.algoarena.dao.repo.ProblemRepo;
import com.algoarena.util.FileReader;
import com.algoarena.util.LanguageCodeModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDetailsService {

    @Autowired
    private ProblemRepo problemRepo;

    @Value("${problems.directory.path}")
    private String problemsDirectory;

    public ProblemDetails getProblemDetails(UUID problemId, String languageId){
        Optional<Problem> problemOptional =  problemRepo.findById(problemId);

        if(problemOptional.isPresent()){
            Problem problem = problemOptional.get();
            log.info("File Path  : "+problemsDirectory + File.separator + problem.getName() + File.separator + LanguageCodeModel.code_lang.get(languageId) + File.separator + "boilerplateCode.txt");
            String initialCode = FileReader.read(problemsDirectory + File.separator + problem.getName() + File.separator + LanguageCodeModel.code_lang.get(languageId) + File.separator + "boilerplateCode.txt");
            String description = FileReader.read(problemsDirectory + File.separator + problem.getName() + File.separator + "description.txt");

            return new ProblemDetails(problem, initialCode,description);
        }else{
            log.info("problemId : "+problemId+" not found!!! in the db.");
        }

        return null;

    }
}
