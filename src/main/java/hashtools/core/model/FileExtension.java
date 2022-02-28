package hashtools.core.model;

import hashtools.core.language.LanguageManager;
import javafx.stage.FileChooser;

import java.util.Optional;

/**
 * <p>Contains the relation between file type and their extensions.</p>
 *
 * <p>Automatically creates the {@link javafx.stage.FileChooser.ExtensionFilter}
 * from the selected option.</p>
 *
 * <h2><b>Filters and Extensions</b></h2>
 *
 * <table style="width:450px" summary="">
 *  <tr>
 *      <th>Filter</th>
 *      <th>Extensions</th>
 *  </tr>
 *  <tr>
 *      <td>All</td>
 *      <td>*</td>
 *  </tr>
 *      <tr>
 *      <td>Compressed</td>
 *      <td>.7z, .ace, .alz, .arc, .arj, .bzip2, .egg, .gz, .gzip, .rar, .tar, .zip</td>
 *      </tr>
 *  <tr>
 *      <td>Disk Image</td>
 *      <td>.adf, .bin, .cue, .dmg, .img, .iso, .nrg</td>
 *  </tr>
 *  <tr>
 *      <td>Document</td>
 *      <td>.doc, .docx, .odp, .ods, .odt, .pdf, .ppt, .pptx, .rtf, .txt, .xls, .xlsx</td>
 *  </tr>
 *  <tr>
 *      <td>Hash</td>
 *      <td>.md5, .sha1, .sha224, .sha256, .sha384, .sha512, .txt</td>
 *  </tr>
 *  <tr>
 *      <td>Markdown</td>
 *      <td>.markdown, .MARKDOWN, .md, .MD</td>
 *  </tr>
 *  <tr>
 *      <td>Markup</td>
 *      <td>.cfml, .gml, .htm, .html, .kml, .xaml, .xhtml, .xml, .yaml</td>
 *  </tr>
 *  <tr>
 *      <td>Music</td>
 *      <td>.aac, .aiff, .mp3, .ogg, .wav, .wma</td>
 *  </tr>
 *  <tr>
 *      <td>Picture</td>
 *      <td>.gif, .jpg, .jpeg, .png, .tiff, .webp</td>
 *  </tr>
 *  <tr>
 *      <td>Runnable</td>
 *      <td>.apk, .appimage, .AppImage, .exe, .jar, .msi, .run</td>
 *  </tr>
 *  <tr>
 *      <td>Script</td>
 *      <td>.bash, .bat, .dat, .js, .php, .sh, .vbs, .zsh</td>
 *  </tr>
 *  <tr>
 *      <td>Source Code</td>
 *      <td>.c, .cpp, .java, .pas, .py</td>
 *  </tr>
 *  <tr>
 *      <td>Stylesheet</td>
 *      <td>.css, .less, .sass, .scss, .styl, .xslt</td>
 *  </tr>
 *  <tr>
 *      <td>Video</td>
 *      <td>.3gp, .avi, .flv, .mkv, .mp4, .mpeg, .mpg, .ogv, .rmvb, .webm, .wmv</td>
 *  </tr>
 * </table>
 *
 * @author Adriano Siqueira
 * @version 3.2.0
 * @since 6.0.0
 */
public enum FileExtension {

    /**
     * <p>Show all files without any restriction.</p>
     */
    ALL("All", "*"),

    /**
     * <p>Only compressed files are shown.</p>
     */
    COMPRESSED("Compresseds", "*.7z", "*.ace", "*.alz", "*.arc", "*.arj", "*.bzip2", "*.egg", "*.gz", "*.gzip", "*.rar", "*.tar", "*.zip"),

    /**
     * <p>Only disc image files are shown.</p>
     */
    DISK_IMAGE("Disc Images", "*.adf", "*.bin", "*.cue", "*.dmg", "*.img", "*.iso", "*.nrg"),

    /**
     * <p>Only document files are shown.</p>
     */
    DOCUMENT("Documents", "*.doc", "*.docx", "*.odp", "*.ods", "*.odt", "*.pdf", "*.ppt", "*.pptx", "*.rtf", "*.txt", "*.xls", "*.xlsx"),

    /**
     * <p>Only hash files are shown.</p>
     */
    HASH("Hashes", "*.md5", "*.sha1", "*.sha224", "*.sha256", "*.sha384", "*.sha512", "*.txt"),

    /**
     * <p>Only markdown files are shown.</p>
     */
    MARKDOWN("Markdown", "*.markdown", "*.MARKDOWN", "*.md", "*.MD"),

    /**
     * <p>Only markup files are shown.</p>
     */
    MARKUP("Markup", "*.cfml", "*.gml", "*.htm", "*.html", "*.kml", "*.xaml", "*.xhtml", "*.xml", "*.yaml"),

    /**
     * <p>Only music files are shown.</p>
     */
    MUSIC("Musics", "*.aac", "*.aiff", "*.mp3", "*.oga", "*.ogg", "*.wav", "*.wma"),

    /**
     * <p>Only picture files are shown.</p>
     */
    PICTURE("Pictures", "*.gif", "*.jpg", "*.jpeg", "*.png", "*.svg", "*.tiff", "*.webp"),

    /**
     * <p>Only runnable files are shown.</p>
     */
    RUNNABLE("Runnables", "*.apk", "*.appimage", "*.AppImage", "*.exe", "*.jar", "*.msi", "*.run"),

    /**
     * <p>Only script files are shown.</p>
     */
    SCRIPT("Scripts", "*.bash", "*.bat", "*.dat", "*.js", "*.php", "*.sh", "*.vbs", "*.zsh"),

    /**
     * <p>Only source code files are shown.</p>
     */
    SOURCE_CODE("Source Codes", "*.c", "*.cpp", "*.java", "*.pas", "*.py"),

    /**
     * <p>Only stylesheet files are shown.</p>
     */
    STYLESHEET("Stylesheet", "*.css", "*.less", "*.sass", "*.scss", "*.styl", "*.xslt"),

    /**
     * <p>Only video files are shown.</p>
     */
    VIDEO("Videos", "*.3gp", "*.avi", "*.flv", "*.mkv", "*.mp4", "*.mpeg", "*.mpg", "*.ogv", "*.rmvb", "*.webm", "*.wmv");

    private final String                      description;
    private final String[]                    extensions;
    private final FileChooser.ExtensionFilter filter;

    FileExtension(String description, String... extensions) {
        this.description = description;
        this.extensions = extensions;
        this.filter = new FileChooser.ExtensionFilter(description, extensions);
    }

    /**
     * <p>Searches for the extension in all enum options.</p>
     *
     * <p>This method returns an {@link Optional} containing the first enum
     * option that contains the extension in its list. If the extension is not
     * found, is empty or null, then an empty Optional is returned.</p>
     *
     * @param extension Extension to be searched for. Does not matter if it
     *                  contains or not the asterisk with a dot.
     *
     * @return An {@link Optional} containing the enum option.
     */
    public static Optional<FileExtension> getByExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return Optional.empty();
        }

        extension = extension.replaceAll("[*.]", "");
        extension = "*." + extension;

        for (FileExtension value : values()) {
            for (String valueExtension : value.extensions) {
                if (valueExtension.equalsIgnoreCase(extension)) {
                    return Optional.of(value);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Gets the enum option group's description.
     *
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the enum option group's extensions.
     *
     * @return the extensions.
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * Gets the {@link javafx.stage.FileChooser.ExtensionFilter} build from the
     * enum option.
     *
     * @return The ExtensionFilter of the enum option.
     */
    public FileChooser.ExtensionFilter getFilter() {
        return new FileChooser.ExtensionFilter(LanguageManager.get(description), extensions);
    }
}
