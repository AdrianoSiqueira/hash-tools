package hashtools.util;

import hashtools.condition.FileExists;
import hashtools.operation.OperationPerformer;
import hashtools.operation.ReplaceFileContent;
import hashtools.operation.ThrowException;

import java.nio.file.Path;

public class FileUtil {

    public static void replaceContent(CharSequence content, Path file) {
        OperationPerformer.perform(
            new FileExists(file),
            new ReplaceFileContent(content, file),
            new ThrowException(new IllegalArgumentException("File does not exists"))
        );
    }
}
