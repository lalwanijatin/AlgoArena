package com.algoarena.boilerplate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class CodeGenerator {

    private ProblemStructureReader problemStructureReader;
    private JavaCodeGenerator javaCodeGenerator;
    private JavaScriptCodeGenerator javaScriptCodeGenerator;

    public void generate(String problemName) throws IOException {
        problemStructureReader.read(problemName);
        javaCodeGenerator.generateBoilerplate(problemName);
        javaCodeGenerator.generateFullCode(problemName);
        javaScriptCodeGenerator.generateBoilerplate(problemName);
        javaScriptCodeGenerator.generateFullCode(problemName);
    }
}
