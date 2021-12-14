package hashtools.core.service;

import aslib.net.ConnectionChecker;
import aslib.net.ConnectionStatus;
import hashtools.core.exception.NoInternetConnectionException;
import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;

import java.util.Optional;

/**
 * <p>
 * Dedicated class to handle web page opening.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class WebService {

    private static HostServices hostServices;

    public static void setHostServices(HostServices hostServices) {
        WebService.hostServices = hostServices;
    }


    /**
     * <p>
     * Open the web page using the text of the {@link Hyperlink} as a url.
     * </p>
     *
     * @param hyperlink Used to obtain the url.
     *
     * @throws IllegalArgumentException      If the hyperlink is null or its text is blank.
     * @throws NoInternetConnectionException If there is no internet connection.
     */
    public void openWebPage(Hyperlink hyperlink)
    throws IllegalArgumentException, NoInternetConnectionException {
        String url = Optional.ofNullable(hyperlink)
                             .map(Hyperlink::getText)
                             .orElseThrow(() -> new IllegalArgumentException("Invalid hyperlink"));

        this.openWebPage(url);
    }

    /**
     * <p>
     * Open the web page using the given url.
     * </p>
     *
     * @param url Web page url.
     *
     * @throws IllegalArgumentException      If the hyperlink is null or its text is blank.
     * @throws NoInternetConnectionException If there is no internet connection.
     */
    public void openWebPage(String url)
    throws IllegalArgumentException, NoInternetConnectionException {
        String link = Optional.ofNullable(url)
                              .map(String::trim)
                              .filter(s -> !s.isBlank())
                              .orElseThrow(() -> new IllegalArgumentException("Invalid url"));

        if (ConnectionChecker.check().equals(ConnectionStatus.OFFLINE))
            throw new NoInternetConnectionException("No internet connection.");

        hostServices.showDocument(link);
    }
}
