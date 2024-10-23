package com.algoarena.boilerplate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemStructure {

    @Value("${problems.directory.path}")
    private String problemsFolderPath;

    private String problemName;
    private String methodName;
    private List<Field> inputFields;
    private List<Field> outputFields;

    @Data
    @AllArgsConstructor
    public class Field {
        private String type;
        private String name;
    }

    public void addInputField(String type, String name){
        if(inputFields == null || inputFields.isEmpty()) inputFields = new ArrayList<>();
        inputFields.add(new Field(type, name));
    }

    public void addOutputField(String type, String name){
        if(outputFields == null || outputFields.isEmpty()) outputFields = new ArrayList<>();
        outputFields.add(new Field(type, name));
    }
}
