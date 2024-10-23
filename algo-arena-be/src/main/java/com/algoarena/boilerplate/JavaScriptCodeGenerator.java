package com.algoarena.boilerplate;

import com.algoarena.boilerplate.model.ProblemStructure;
import com.algoarena.boilerplate.model.ProblemStructures;
import com.algoarena.util.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Collectors;

@Component
public class JavaScriptCodeGenerator {
    @Value("${problems.directory.path}")
    private String problemsFolderPath;

    public void generateBoilerplate(String problemName){

        ProblemStructure problemStructure = ProblemStructures.problemName_problemStructure.get(problemName);

        // Generate parameters
        String params = problemStructure.getInputFields().stream().map(field -> field.getName()).collect(Collectors.joining(","));

        String boilerplateCode = "function "+problemStructure.getMethodName()+ "("+params+"){\n\t//Implementation goes here\n\treturn result;\n}";

        String filePathToStoreBoilerplate = problemsFolderPath+ File.separator +
                problemStructure.getProblemName()+File.separator +
                "javascript"+File.separator +
                "boilerplateCode.txt";

        FileWriter.write(boilerplateCode,filePathToStoreBoilerplate);
    }

    public void generateFullCode(String problemName){

        ProblemStructure problemStructure = ProblemStructures.problemName_problemStructure.get(problemName);

        String readValuesFromInputFile = problemStructure.getInputFields().stream().map(field -> {
            if(field.getType().startsWith("list<")){
                return "const "+field.getName()+"_size = parseInt(input.shift());\nconst "+field.getName()+" = input.splice(0,"+field.getName()+"_size).map(Number);\n";
            }else{
                return "const "+field.getName()+" = parseInt(input.shift());\n";
            }
        }).collect(Collectors.joining("\n"));

        String functionCall = "console.log("+problemStructure.getMethodName()+"("+problemStructure.getInputFields().stream().map(field -> field.getName()).collect(Collectors.joining(","))+"));";

        String inputFilePath = "/dev/problems/"+
                problemStructure.getProblemName()+"/"+
                "input/"+
                "##INPUT_FILE_NAME##.txt";
        String readInputFile = "const input = require('fs').readFileSync('"+inputFilePath+"', 'utf-8').split('\\n').join(' ').split(' ')";

        String fullCode = "##USER_CODE_GOES_HERE##\n\n"+readInputFile+"\n\n"+readValuesFromInputFile+"\n\n"+functionCall;

        String filePathToStoreFullCode = problemsFolderPath+ File.separator +
                problemStructure.getProblemName()+File.separator +
                "javascript"+File.separator +
                "fullCode.txt";

        FileWriter.write(fullCode,filePathToStoreFullCode);

    }
}
