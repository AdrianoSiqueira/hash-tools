package hashtools.core.service;

import javafx.application.HostServices;

import java.net.URL;

/**
 * <p>
 * Performs network operations.
 * </p>
 */
public class WebService {

    private HostServices service;

    public WebService(HostServices service) {
        this.service = service;
    }

    public HostServices getHostServices() {
        return service;
    }

    /**
     * <p>
     * Opens the given {@link URL} with the operating system's
     * default web browser.
     * </p>
     *
     * @param url URL to open.
     */
    public void openWebPage(String url) {
        service.showDocument(url);
    }
}
