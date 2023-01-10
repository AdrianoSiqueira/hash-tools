package hashtools.core.service;

import hashtools.core.model.FileExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public boolean isHashExtension(String extension) {
        switch (extension) {
            case "md5":
            case "sha1":
            case "sha224":
            case "sha256":
            case "sha384":
            case "sha512":
            case "txt":
                return true;
            default:
                return false;
        }
    }

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

    public void write(String content, Path destination, OpenOption... openOptions) {
        try {
            Files.writeString(destination, content, openOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
