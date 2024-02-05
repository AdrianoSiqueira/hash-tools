package hashtools.domain;

import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ResourceBundle;

@RequiredArgsConstructor
public enum ExtensionFilter {

    ALL(
        "hashtools.domain.extension_filter.all",
        List.of("*")
    ),

    HASH(
        "hashtools.domain.extension_filter.hash",
        List.of("*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512", "*.txt")
    );

    private final String       title;
    private final List<String> extensions;

    public final FileChooser.ExtensionFilter get(ResourceBundle language) {
        return new FileChooser.ExtensionFilter(
            language.getString(title),
            extensions
        );
    }
}
