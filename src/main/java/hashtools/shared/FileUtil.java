package hashtools.shared;

import hashtools.shared.operation.Operation;
import hashtools.shared.operation.ReplaceFileContent;

import java.nio.file.Path;

public class FileUtil {

    public static void replaceContent(CharSequence content, Path file) {
        Operation.perform(new ReplaceFileContent(content, file));
    }
}
