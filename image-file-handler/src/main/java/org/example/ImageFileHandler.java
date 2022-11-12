package org.example;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class ImageFileHandler extends FileHandler {

    @Override
    public Boolean isExtensionCorrect(File file) {
        if (!file.isFile())
            return false;
        String extension = getFileExtension(file);
        for (String ext: new String[]{"jpeg", "png", "bmp", "gif"})
            if (extension.equals(ext))
                return true;

        return false;
    }

    @Override
    public Map<String, String> getFunctionsNamesWithDescriptions() {
        Map<String, String> namesWithDescriptions = super.getFunctionsNamesWithDescriptions();
        namesWithDescriptions.put("getMetadata", "Возвращает метаданные в формате Map<String, String>, где глючи это названия тегов, а значения - значения тегов.");
        namesWithDescriptions.put("getResolution", "Возвращает разрешение изображения в виде Collection<Integer> размера 2, где первый элемент - ширина, второй - высота.");

        return namesWithDescriptions;
    }

    public Map<String, String> getMetadata(File file) {
        try {
            Map<String, String> metadataTagsWithValues = new HashMap<>();

            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    metadataTagsWithValues.put(tag.getTagName(), tag.getDescription());
                }
            }
            return metadataTagsWithValues;
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Integer> getResolution(File file) {
        try {
            Integer[] result = new Integer[2];

            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagName().equals("Image Width"))
                        result[0] = Integer.parseInt(tag.getDescription());
                    else if (tag.getTagName().equals("Image Height"))
                        result[1] = Integer.parseInt(tag.getDescription());
                }
            }

            return Arrays.asList(result);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
