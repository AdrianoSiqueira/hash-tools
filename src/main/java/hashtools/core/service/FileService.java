package hashtools.core.service;

import hashtools.core.model.FileExtension;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public boolean pathHasRequiredExtension(Path path, FileExtension fileExtension) {
        String fileName   = path.getFileName().toString();
        int    indexOfDot = fileName.lastIndexOf(".");

        boolean fileHasNoExtension = indexOfDot < 1;
        boolean allFilesAllowed    = fileExtension == FileExtension.ALL;

        if (fileHasNoExtension) return allFilesAllowed;
        else if (allFilesAllowed) return true;

        String extension = "*" + fileName.substring(indexOfDot);

        return List.of(fileExtension.getExtensions())
                   .contains(extension);
    }

    public boolean stringIsFilePath(String string) {
        return string != null &&
               Files.isRegularFile(Path.of(string));
    }

    @SneakyThrows
    public void write(String content, Path path, OpenOption... openOptions) {
        Files.writeString(path, content, openOptions);
    }
}
