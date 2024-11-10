package hashtools.shared;

import hashtools.shared.operation.OperationPerformer;
import hashtools.shared.operation.ReplaceFileContent;

import java.nio.file.Path;

public class FileUtil {

    public static void replaceContent(CharSequence content, Path file) {
        OperationPerformer.perform(
            new ReplaceFileContent(content, file)
        );
    }
}
