package hashtools.core.service;

import aslib.net.ConnectionChecker;
import aslib.net.ConnectionStatus;
import hashtools.core.exception.InvalidUrlException;
import hashtools.core.exception.NoInternetConnectionException;
import javafx.application.HostServices;
import javafx.scene.control.Hyperlink;

/**
 * <p>
 * Dedicated class to handle web page opening.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 2.0.1
 * @since 2.0.0
 */
public class WebService {

    @Deprecated private static HostServices hostServices;

    /**
     * <p>
     * Opens the given url in default web browser.
     * </p>
     *
     * @param hostServices Used to open web pages.
     * @param url          Url that will be opened.
     *
     * @throws IllegalArgumentException      If the url is invalid.
     * @throws NoInternetConnectionException If system is offline.
     */
    public static void openWebPage(HostServices hostServices, String url)
    throws InvalidUrlException, NoInternetConnectionException {
        if (urlIsInvalid(url)) throw new InvalidUrlException("Invalid url");
        else if (systemIsOffline()) throw new NoInternetConnectionException("No internet connection");

        hostServices.showDocument(url);
    }

    /**
     * <p>
     * Opens the hyperlink's url in default web browser.
     * </p>
     *
     * @param hostServices Used to open web pages.
     * @param hyperlink    From where to get the url.
     *
     * @throws IllegalArgumentException      If the url is invalid.
     * @throws NoInternetConnectionException If system is offline.
     */
    @Deprecated
    public static void openWebPage(HostServices hostServices, Hyperlink hyperlink)
    throws IllegalArgumentException, NoInternetConnectionException {
        if (hyperlinkIsInvalid(hyperlink)) throw new IllegalArgumentException("Invalid hyperlink");

        openWebPage(hostServices, hyperlink.getText());
    }

    @Deprecated
    public static void setHostServices(HostServices hostServices) {
        WebService.hostServices = hostServices;
    }

    /**
     * <p>
     * Checks whether hyperlink is invalid or not.
     * </p>
     *
     * @param hyperlink Hyperlink that will be checked.
     *
     * @return TRUE if it is invalid.
     */
    private static boolean hyperlinkIsInvalid(Hyperlink hyperlink) {
        return hyperlink == null;
    }

    /**
     * <p>
     * Checks if system is offline.
     * </p>
     *
     * @return TRUE if it is offline.
     */
    private static boolean systemIsOffline() {
        return ConnectionChecker.check() == ConnectionStatus.OFFLINE;
    }

    /**
     * <p>
     * Checks whether url is invalid or not.
     * </p>
     *
     * @param url Url that will be checked.
     *
     * @return TRUE if it is invalid.
     */
    private static boolean urlIsInvalid(String url) {
        return url == null ||
               url.isBlank();
    }
}
