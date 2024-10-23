package com.algoarena.boilerplate;

import com.algoarena.boilerplate.model.ProblemStructure;
import com.algoarena.boilerplate.model.ProblemStructures;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ProblemStructureReader {
    private final String PROBLEM_NAME_REGEX = ": \"(.*)\"$";
    private final String FUNCTION_NAME_REGEX = ": (\\w+)$";
    private final String FIELD_NAME_REGEX = ": (\\w+(?:<\\w+>)?) (\\w+)$";

    private ProblemStructure problemStructure;

    @Value("${problems.directory.path}")
    private String problemsFolder;

    public void read(String problemName) throws IOException {
        problemStructure = new ProblemStructure();
        String problemStructureFilePath = problemsFolder + File.separator + problemName + File.separator + "structure.txt";

        try(FileInputStream fis = new FileInputStream(problemStructureFilePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);){

            String currentSection = null;

            String line = bufferedReader.readLine();

            while(line != null){
                line = line.trim();
                if(line.isEmpty()) continue;

                if(line.startsWith("Problem Name")){
                    Pattern pattern = Pattern.compile(PROBLEM_NAME_REGEX); // Compile the pattern
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        problemStructure.setProblemName(matcher.group(1));
                    }else{
                        throw new RuntimeException("An error occurred while parsing the Problem Name.");
                    }
                }else if(line.startsWith("Function Name")){
                    Pattern pattern = Pattern.compile(FUNCTION_NAME_REGEX); // Compile the pattern
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        problemStructure.setMethodName(matcher.group(1));
                    } else {
                        throw new RuntimeException("An error occurred while parsing the Function Name.");
                    }
                }else if(line.startsWith("Input Field") && currentSection.equals("Input")){
                    Pattern pattern = Pattern.compile(FIELD_NAME_REGEX); // Compile the pattern
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        problemStructure.addInputField(matcher.group(1), matcher.group(2));
                    } else {
                        throw new RuntimeException("An error occurred while parsing the Input Field");
                    }
                }else if(line.startsWith("Output Field") && currentSection.equals("Output")){
                    Pattern pattern = Pattern.compile(FIELD_NAME_REGEX); // Compile the pattern
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        problemStructure.addOutputField(matcher.group(1), matcher.group(2));
                    } else {
                        throw new RuntimeException("An error occurred while parsing the Output Field");
                    }
                }else if(line.equalsIgnoreCase("Input Structure:")) {
                    currentSection = "Input";
                }else if(line.equalsIgnoreCase("Output Structure:")){
                    currentSection = "Output";
                }else{
                    throw new RuntimeException("An error occurred while parsing the Problem Structure. Please verify the same.");
                }

                line = bufferedReader.readLine();
            }
            ProblemStructures.problemName_problemStructure.put(problemStructure.getProblemName(), problemStructure);

        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }
    }
}
