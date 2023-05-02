package hashtools.core.model;

import hashtools.core.service.LanguageService;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * <p>
 * Used to create the {@link ExtensionFilter} of the dialog in the gui.
 * </p>
 */
public enum FileExtension {

    ALL("All", "*"),
    HASH("Hashes", "*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512", "*.txt");


    private final String          description;
    private final String[]        extensions;
    private       LanguageService languageService;


    FileExtension(String description, String... extensions) {
        this.description     = description;
        this.extensions      = extensions;
        this.languageService = new LanguageService();
    }

    public String getDescription() {
        return description;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public ExtensionFilter getFilter() {
        return new ExtensionFilter(
                languageService.get(description),
                extensions
        );
    }
}
