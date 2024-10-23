package com.algoarena.codesubmit;

import com.algoarena.boilerplate.ProblemStructureReader;
import com.algoarena.boilerplate.model.ProblemStructure;
import com.algoarena.boilerplate.model.ProblemStructures;
import com.algoarena.dao.entity.Submission;
import com.algoarena.dao.repo.ProblemRepo;
import com.algoarena.dao.repo.SubmissionRepo;
import com.algoarena.dao.repo.SubmissionsRepo;
import com.algoarena.util.LanguageCodeModel;
import com.algoarena.util.UserDetailsGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.*;

@Service
public class CodeSubmitter {

    @Value("${problems.directory.path}")
    private String problemsFolder;

    @Value("${judge0.batch.submission.uri}")
    private String SUBMISSION_URL_BATCH;

    private ProblemStructure problemStructure;
    private SubmissionRepo submissionRepo;
    private ProblemRepo problemRepo;
    private UserDetailsGetter userDetailsGetter;
    private ObjectMapper objectMapper;
    private SubmissionsRepo submissionsRepo;
    private RestTemplate restTemplate;
    private ProblemStructureReader problemStructureReader;


    public CodeSubmitter(SubmissionRepo submissionRepo, ProblemRepo problemRepo, UserDetailsGetter userDetailsGetter, ObjectMapper objectMapper, SubmissionsRepo submissionsRepo, RestTemplate restTemplate, ProblemStructureReader problemStructureReader){
        this.submissionRepo = submissionRepo;
        this.problemRepo = problemRepo;
        this.userDetailsGetter = userDetailsGetter;
        this.objectMapper = objectMapper;
        this.submissionsRepo = submissionsRepo;
        this.restTemplate = restTemplate;
        this.problemStructureReader = problemStructureReader;
    }

    public UUID submit(String problemName, String userCode, String languageId) throws IOException {
        String fullCodePath = problemsFolder+ File.separator+
                problemName+File.separator+
                LanguageCodeModel.code_lang.get(languageId) +File.separator+
                "fullCode.txt";

        if(!ProblemStructures.problemName_problemStructure.containsKey(problemName)){
            problemStructureReader.read(problemName);
        }
        problemStructure = ProblemStructures.problemName_problemStructure.get(problemName);

        String fullCode = readContentsOf(fullCodePath);

        String fullCodePlusUserCode = fullCode.replace("##USER_CODE_GOES_HERE##", userCode);

        String outputFolderPath = problemsFolder+ File.separator+
                problemName+File.separator+
                "output"+File.separator;


        List<String> sourceCodeList = new ArrayList<>();
        List<String> expectedOutputList = new ArrayList<>();

        int fileName = 0;
        String filePath = outputFolderPath+fileName+".txt";
        while(new File(filePath).exists()){
            String fullSrcCode = fullCodePlusUserCode.replace("##INPUT_FILE_NAME##",String.valueOf(fileName));
            sourceCodeList.add(fullSrcCode);
            String expectedOutput = getExpectedOutput(filePath);
            expectedOutputList.add(expectedOutput);

            fileName++;
            filePath = outputFolderPath+fileName+".txt";
        }

        String codeExecutorResponse = executeCodeBatch(sourceCodeList, expectedOutputList,languageId, null);

        UUID submissionId = insertDataInSubmission(problemName, userCode);

        updateSubmissions(codeExecutorResponse, submissionId);

        return submissionId;
    }

    private String executeCodeBatch(List<String> code, List<String> expectedOutput, String languageId, String callbackURL) throws JsonProcessingException {

        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        for(int i=0;i<expectedOutput.size();i++){
            Map<String, Object> submission = new HashMap<>();
            submission.put("source_code", code.get(i));
            submission.put("expected_output", expectedOutput.get(i));
            submission.put("language_id", languageId);
            if(callbackURL != null) submission.put("callback_url", callbackURL);

            requestBody.add("submissions", submission);
        }


        String requestBodyJsonString = objectMapper.writeValueAsString(requestBody);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity with headers and body
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJsonString, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                SUBMISSION_URL_BATCH,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return response.getBody();
    }

    private String readContentsOf(String filePath){

        StringBuilder fileContent = new StringBuilder();

        try(FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);){

            String line = bufferedReader.readLine();
            while(line != null){
                fileContent.append(line).append("\n");
                line = bufferedReader.readLine();
            }


        }catch(IOException e){
            e.printStackTrace();
            return null;
        }

        return fileContent.toString();
    }

    private String getExpectedOutput(String outputFilePath){
        String contents = readContentsOf(outputFilePath);

        ProblemStructure.Field outputField = problemStructure.getOutputFields().get(0);

        if(outputField.getType().startsWith("list<")){
            String[] contentsArr = contents.trim().split("\n");
            return contentsArr[1];
        }else{
            return contents.trim();
        }
    }

    private UUID insertDataInSubmission(String problemName, String userCode) {

        UUID problemUUID = problemRepo.findIdByName(problemName);

        Submission submission = new Submission();
        submission.setCode(userCode);
        submission.setProblemId(problemUUID);
        submission.setUsername(userDetailsGetter.getUserName());

        Submission savedSubmission = submissionRepo.save(submission);

        return savedSubmission.getId();

    }

    private void updateSubmissions(String codeExecutorResponse, UUID submissionId) throws JsonProcessingException {
        List<Map<String, String>> listOfTokenMap = objectMapper.readValue(codeExecutorResponse, new TypeReference<List<Map<String, String>>>() {});
        List<String> tokens = new ArrayList<>();
        for(Map<String, String> token : listOfTokenMap) tokens.add(token.get("token"));

        submissionsRepo.updateFieldByTokens(submissionId, tokens);
    }
}
