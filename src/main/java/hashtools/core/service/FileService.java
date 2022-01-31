package hashtools.core.service;

import aslib.filemanager.FileExtension;

import java.nio.file.Path;
import java.util.List;

public class FileService {

    public static boolean pathHasRequiredExtension(Path path, FileExtension fileExtension) {
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
}
