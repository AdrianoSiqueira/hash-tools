package hashtools.shared.operation;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@RequiredArgsConstructor
public class ReplaceFileContent extends Operation {

    private final CharSequence content;
    private final Path file;

    @Override
    protected void perform() {
        try {
            Files.writeString(
                file,
                content,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
