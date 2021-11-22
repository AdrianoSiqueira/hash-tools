package hashtools.core.supply;

/**
 * <p>Dedicated to storing some useful links.</p>
 *
 * @author Adriano Siqueir
 * @version 1.0.0
 * @since 1.0.0
 */
public enum Links {

    DEVELOPER_PROFILE("https://github.com/AdrianoSiqueira"),
    ONLINE_DOCUMENTATION("https://github.com/AdrianoSiqueira/HashTools/wiki"),
    PROJECT("https://github.com/AdrianoSiqueira/HashTools");


    private final String url;


    Links(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
