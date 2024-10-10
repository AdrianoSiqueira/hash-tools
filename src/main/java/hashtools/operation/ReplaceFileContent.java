package hashtools.operation;

import hashtools.service.FileService;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class ReplaceFileContent implements Operation {

    private final String content;
    private final Path file;

    @Override
    public void perform() {
        FileService service = new FileService();
        service.replaceContent(content, file);
    }
}
