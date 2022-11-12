package org.example;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
public class DirectoryFileHandler extends FileHandler{
    @Override
    public Boolean isExtensionCorrect(File file) {
        return file.isDirectory();
    }

    @Override
    public Map<String, String> getFunctionsNamesWithDescriptions() {
        Map<String, String> namesWithDescriptions = super.getFunctionsNamesWithDescriptions();
        namesWithDescriptions.put("getSubfilesNames", "Возвращает список названий файлов в каталоге. Collection<String>");
        namesWithDescriptions.put("getParent", "Возвращает название родительского каталага.");

        return namesWithDescriptions;
    }

    public Collection<String> getSubfilesNames(File file) {
        ArrayList<String> result = new ArrayList<>();
        for (String subfile : file.list())
            result.add(subfile);

        return result;
    }

    public String getParent(File file) {
        return file.getParent();
    }
}
