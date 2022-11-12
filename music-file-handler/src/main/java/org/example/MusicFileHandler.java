package org.example;

import com.groupdocs.metadata.Id3v1Tag;
import com.groupdocs.metadata.Metadata;
import com.groupdocs.metadata.Mp3Format;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;
import java.util.Arrays;
import java.util.Map;

@Component
public class MusicFileHandler extends FileHandler{
    @Override
    public Boolean isExtensionCorrect(File file) {
        if (!file.isFile())
            return false;
        String extension = getFileExtension(file);
        for (String ext: new String[]{"mp3"})
            if (extension.equals(ext))
                return true;

        return false;
    }

    @Override
    public Map<String, String> getFunctionsNamesWithDescriptions() {
        Map<String, String> namesWithDescriptions = super.getFunctionsNamesWithDescriptions();
        namesWithDescriptions.put("getName", "Возвращает название трека.");
        namesWithDescriptions.put("getDuration", "Возвращает продолжительность трека в секундах. Integer");

        return namesWithDescriptions;
    }

    public String getName(File file) {
        Id3v1Tag format = new Mp3Format(file.getPath()).getId3v1();

        return format.getArtist() + " - " + format.getTitle();
    }

    public Integer getDuration(File file) {
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            return audioFile.getAudioHeader().getTrackLength();
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
