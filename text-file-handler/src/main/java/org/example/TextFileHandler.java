package org.example;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TextFileHandler extends FileHandler {
    @Override
    public Boolean isExtensionCorrect(File file) {
        if (!file.isFile())
            return false;
        return getFileExtension(file).equals("txt");
    }

    @Override
    public Map<String, String> getFunctionsNamesWithDescriptions() {
        Map<String, String> methodsWithDescriptions = super.getFunctionsNamesWithDescriptions();
        methodsWithDescriptions.put("getLineNumber", "Возвращает число строк в файле. int");
        methodsWithDescriptions.put("getCharsWithCounts", "Возвращает Map<Character, Integer>, где ключами служат каждый встреченный в файле символ, а значения это количество вхождения соответствующего символа.");

        return methodsWithDescriptions;
    }

    public int getLineNumber(File file) {
        try(FileReader reader = new FileReader(file)){
            int lineNum = 0;
            int letter;
            while ((letter = reader.read()) != -1) {
                if (letter == 13)
                    lineNum++;
            }
            return lineNum+1;
        }catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Map<Character, Integer> getCharsWithCounts(File file) {
        try(FileReader reader = new FileReader(file)){
            HashMap<Character, Integer> dict = new HashMap<Character, Integer>();
            int letter;
            while ((letter = reader.read()) != -1) {
                if (dict.containsKey((char)letter))
                    dict.replace((char)letter, dict.get((char)letter) + 1);
                else
                    dict.put((char)letter, 1);
            }
            return dict;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
