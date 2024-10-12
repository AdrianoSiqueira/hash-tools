package hashtools.util;

import hashtools.operation.OperationPerformer;
import hashtools.operation.ReplaceFileContent;

import java.nio.file.Path;

public class FileUtil {

    public static void replaceContent(CharSequence content, Path file) {
        OperationPerformer.perform(
            new ReplaceFileContent(content, file)
        );
    }
}
