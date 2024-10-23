package com.algoarena.boilerplate;

import com.algoarena.boilerplate.model.ProblemStructure;
import com.algoarena.boilerplate.model.ProblemStructures;
import com.algoarena.util.FileWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Collectors;

@Component
public class JavaCodeGenerator {

    @Value("${problems.directory.path}")
    private String problemsFolderPath;

    public void generateBoilerplate(String problemName){

        ProblemStructure problemStructure = ProblemStructures.problemName_problemStructure.get(problemName);

        StringBuilder input = new StringBuilder();
        problemStructure.getInputFields().forEach(field -> {
            input.append(convertType(field.getType())).append(" ").append(field.getName()).append(",");
        });
        input.deleteCharAt(input.length()-1);

        String javaCode =
                "private static "+convertType(problemStructure.getOutputFields().get(0).getType())+" "+problemStructure.getMethodName()+"("+input.toString()+"){\n"+
                        "\t//Implementation goes here\n"+
                        "}";

        String filePathToStoreBoilerplate = problemsFolderPath+ File.separator +
                problemStructure.getProblemName()+File.separator +
                "java"+File.separator +
                "boilerplateCode.txt";

        FileWriter.write(javaCode,filePathToStoreBoilerplate);
    }

    public void generateFullCode(String problemName){

        ProblemStructure problemStructure = ProblemStructures.problemName_problemStructure.get(problemName);

        // Declare input fields
        String declareInputFields = problemStructure.getInputFields().stream().map(field -> {
            return field.getType().startsWith("list") ? "\t\t"+convertType(field.getType())+" "+field.getName()+" = new ArrayList<>();" : "\t\t"+convertType(field.getType())+" "+field.getName()+";";
        }).collect(Collectors.joining("\n"));


        // Read input values
        String readInputValues = "\t\ttry(FileInputStream fis = new FileInputStream(\"/dev/problems/"+problemStructure.getProblemName()+"/input/##INPUT_FILE_NAME##.txt\");\n\t\t\tInputStreamReader isr = new InputStreamReader(fis);\n\t\t\tBufferedReader bufferedReader = new BufferedReader(isr);){\n";
        readInputValues += problemStructure.getInputFields().stream().map(field -> {
            if(field.getType().startsWith("list")){
                return "\t\t\t\tString line = bufferedReader.readLine(); //Skipping the size of the list\n\t\t\t\tline = bufferedReader.readLine().trim();\n\t\t\t\tString[] parts = line.split(\" \");\n\t\t\t\tfor(String part : parts){\n" +
                        "\t\t\t\t\t"+field.getName()+".add("+getStringToTypeConverter(field.getType())+");\n\t\t\t\t}";
            }else{
                return "\t\t\t\tString part = bufferedReader.readLine().trim();\n"+
                        "\t\t\t\t"+field.getName()+" = "+getStringToTypeConverter(field.getType())+";\n";
            }
        }).collect(Collectors.joining("\n"));
        readInputValues += "\n\t\t}catch(IOException e){e.printStackTrace();}\n";


        //function call
        String functionCall = problemStructure.getMethodName()+"("+problemStructure.getInputFields().stream().map(field -> field.getName()).collect(Collectors.joining(","))+")";


        // Print output
        String printOutput = "";
        if(problemStructure.getOutputFields().get(0).getType().startsWith("list")){
            printOutput = "\t\t"+functionCall+".forEach(ele -> System.out.println(ele+\" \");";
        }else{
            printOutput = "\t\tSystem.out.println("+functionCall+");";
        }


        String code = "import java.util.*;\nimport java.io.*;\nimport java.nio.file.*;\n\npublic class Main{\n\npublic static void main(String... args){\n" +
                declareInputFields + "\n" +
                readInputValues +  "\n" +
                printOutput + "\n\n" +
                "}\n\t\t##USER_CODE_GOES_HERE##" + "\n"+
                "\n}";

        FileWriter.write(code, problemsFolderPath+ File.separator + problemStructure.getProblemName()+File.separator + "java"+File.separator + "fullCode.txt");
    }


    private String convertType(String type){
        switch(type){
            case "int":
                return "int";
            case "long":
                return "long";
            case "double":
                return "double";
            case "float":
                return "float";
            case "string":
                return "String";
            case "char":
                return "char";
            case "bool":
                return "boolean";
            case "list<int>":
                return "List<Integer>";
            case "list<long>":
                return "List<Long>";
            case "list<float>":
                return "List<Float>";
            case "list<string>":
                return "List<String>";
            case "list<bool>":
                return "List<Boolean>";
            default:
                throw new RuntimeException("Unknown data type!");
        }
    }

    private String getStringToTypeConverter(String fieldType){
        switch(fieldType){
            case "list<int>":
            case "int":
                return "Integer.parseInt(part)";
            case "list<float>":
            case "float":
                return "Float.parseFloat(part)";
            case "list<double>":
            case "double":
                return "Double.parseDouble(part)";
            case "list<bool>":
            case "bool":
                return "Boolean.parseBoolean(part)";
            case "list<string>":
            case "string":
                return "part";
            default:
                throw new RuntimeException("Unknown data type!");
        }
    }
}
