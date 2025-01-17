package hashtools.coremodule;

import javafx.stage.FileChooser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
public enum Extension {

    ALL(
        "extension.all",
        List.of("*")
    ),

    COMPRESSED(
        "extension.compressed",
        List.of("*.7z", "*.ace", "*.alz", "*.arc", "*.arj", "*.bzip2", "*.egg", "*.gz", "*.gzip", "*.rar", "*.tar", "*.zip")
    ),

    DISK_IMAGE(
        "extension.disc-image",
        List.of("*.adf", "*.bin", "*.cue", "*.dmg", "*.img", "*.iso", "*.nrg")
    ),

    DOCUMENT(
        "extension.document",
        List.of("*.doc", "*.docx", "*.odp", "*.ods", "*.odt", "*.pdf", "*.ppt", "*.pptx", "*.rtf", "*.txt", "*.xls", "*.xlsx")
    ),

    HASH(
        "extension.hash",
        List.of("*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512", "*.txt")
    ),

    MARKDOWN(
        "extension.markdown",
        List.of("*.markdown", "*.MARKDOWN", "*.md", "*.MD")
    ),

    MARKUP(
        "extension.markup",
        List.of("*.cfml", "*.gml", "*.htm", "*.html", "*.kml", "*.xaml", "*.xhtml", "*.xml", "*.yaml")
    ),

    MUSIC(
        "extension.music",
        List.of("*.aac", "*.aiff", "*.mp3", "*.oga", "*.ogg", "*.wav", "*.wma")
    ),

    PICTURE(
        "extension.picture",
        List.of("*.gif", "*.jpg", "*.jpeg", "*.png", "*.svg", "*.tiff", "*.webp")
    ),

    RUNNABLE(
        "extension.runnable",
        List.of("*.apk", "*.appimage", "*.AppImage", "*.exe", "*.jar", "*.msi", "*.run")
    ),

    SCRIPT(
        "extension.script",
        List.of("*.bash", "*.bat", "*.dat", "*.js", "*.php", "*.sh", "*.vbs", "*.zsh")
    ),

    SOURCE_CODE(
        "extension.source-code",
        List.of("*.c", "*.cpp", "*.java", "*.pas", "*.py")
    ),

    STYLESHEET(
        "extension.stylesheet",
        List.of("*.css", "*.less", "*.sass", "*.scss", "*.styl", "*.xslt")
    ),

    VIDEO(
        "extension.video",
        List.of("*.3gp", "*.avi", "*.flv", "*.mkv", "*.mp4", "*.mpeg", "*.mpg", "*.ogv", "*.rmvb", "*.webm", "*.wmv")
    );

    private final String name;
    private final List<String> extensions;


    public static Collection<FileChooser.ExtensionFilter> getAllExtensions(ResourceBundle language) {
        return Stream
            .of(values())
            .map(extension -> new FileChooser.ExtensionFilter(
                extension.translate(language, extension.name),
                extension.extensions
            ))
            .toList();
    }


    public final FileChooser.ExtensionFilter getFilter(ResourceBundle language) {
        return new FileChooser.ExtensionFilter(
            translate(language, name),
            extensions
        );
    }

    private String translate(ResourceBundle dictionary, String entry) {
        try {
            return dictionary.getString(entry);
        } catch (NullPointerException e) {
            log.error("The dictionary or entry is null", e);
        } catch (MissingResourceException e) {
            log.error("The entry was not found in dictionary", e);
        } catch (Exception e) {
            log.error("Unknown issue", e);
        }

        return entry;
    }
}
