package org.example;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class FileHandler {
    public abstract Boolean isExtensionCorrect(File file);
    public Map<String, String> getFunctionsNamesWithDescriptions() {
        HashMap<String, String> methodsWithDescriptions = new HashMap<>();
        methodsWithDescriptions.put("getFileSize", "Возвращает размер файла в байтах. long");

        return methodsWithDescriptions;
    }
    public Object Invoke(String functionName, File file) {
        try {
            return this.getClass().getMethod(functionName, file.getClass()).invoke(this, file);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getFileSize(File file) {
        return file.length();
    }

    protected String getFileExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf('.')+1);
    }
}
