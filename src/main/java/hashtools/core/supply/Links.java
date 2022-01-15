package hashtools.core.supply;

/**
 * <p>Dedicated to storing some useful links.</p>
 *
 * @author Adriano Siqueir
 * @version 1.1.1
 * @since 1.0.0
 */
public enum Links {

    APPLICATION_ONLINE_DOCUMENTATION("https://github.com/AdrianoSiqueira/HashTools/wiki");


    private final String url;


    Links(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
