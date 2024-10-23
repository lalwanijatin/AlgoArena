package com.algoarena.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {
    public static String read(String filePath){

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
}
