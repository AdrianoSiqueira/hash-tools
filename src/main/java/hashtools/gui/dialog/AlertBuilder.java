package hashtools.gui.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Builder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Dedicated class to create dialogs. It can create both message and stack trace
 * dialogs.
 * </p>
 *
 * <p>
 * To create stack trace dialog, it it necessary to provide a throwable object,
 * then it will create an expandable section in the dialog.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.0
 * @since 2.0.0
 */
public class AlertBuilder implements Builder<Alert> {

    private Alert.AlertType alertType;

    private String title;
    private String headerText;
    private String contentText;

    private Throwable throwable;

    private ButtonType[] buttons;

    private String styleClass;
    private String id;

    private URL favIcon;
    private URL headerIcon;
    private URL stylesheet;

    private boolean resizable = true;
    private boolean expanded;


    public AlertBuilder alertType(Alert.AlertType alertType) {
        this.alertType = alertType;
        return this;
    }

    public AlertBuilder buttons(ButtonType... buttons) {
        List<ButtonType> buttonList = Optional.ofNullable(buttons)
                                              .map(Arrays::asList)
                                              .orElse(Collections.emptyList())
                                              .stream()
                                              .filter(Objects::nonNull)
                                              .toList();

        this.buttons = buttonList.isEmpty()
                       ? null
                       : buttonList.toArray(new ButtonType[0]);
        return this;
    }

    public AlertBuilder contentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    public AlertBuilder expanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }

    public AlertBuilder favIcon(URL favIcon) {
        this.favIcon = favIcon;
        return this;
    }

    public AlertBuilder headerIcon(URL headerIcon) {
        this.headerIcon = headerIcon;
        return this;
    }

    public AlertBuilder headerText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    public AlertBuilder id(String id) {
        this.id = id;
        return this;
    }

    public AlertBuilder resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public AlertBuilder styleClass(String styleClass) {
        this.styleClass = styleClass;
        return this;
    }

    public AlertBuilder stylesheet(URL stylesheet) {
        this.stylesheet = stylesheet;
        return this;
    }

    public AlertBuilder throwable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public AlertBuilder title(String title) {
        this.title = title;
        return this;
    }


    private void configureDialogPane(Alert alert) {
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
        dialogPane.setExpanded(expanded);
        dialogPane.getStyleClass().add(determineStyleClass());
        dialogPane.setId(determineId());

        Optional.ofNullable(buttons)
                .ifPresent(dialogPane.getButtonTypes()::setAll);

        Optional.ofNullable(favIcon)
                .ifPresent(f -> setFavIcon(dialogPane, f));

        Optional.ofNullable(stylesheet)
                .ifPresent(s -> setStylesheet(dialogPane, s));

        Optional.ofNullable(throwable)
                .ifPresent(t -> configureStackTraceContent(dialogPane, t));
    }

    private void configureStackTraceContent(DialogPane dialogPane, Throwable throwable) {
        TextArea textArea = new TextArea();
        textArea.setText(getStackTraceAsString(throwable));
        textArea.setEditable(false);

        dialogPane.setExpandableContent(textArea);
    }

    private Alert createAndConfigureAlertInstance() {
        Alert alert = new Alert(determineAlertType());
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(determineContentText());
        alert.setResizable(resizable);

        Optional.ofNullable(headerIcon)
                .ifPresent(i -> setHeaderIcon(alert, i));

        return alert;
    }

    private ImageView createImageView(String url) {
        ImageView view = new ImageView(url);
        view.setPreserveRatio(true);
        view.setFitWidth(48);

        return view;
    }

    private Alert.AlertType determineAlertType() {
        alertType = Optional.ofNullable(alertType)
                            .orElseGet(this::determineAlertTypeAccordingToThrowable);

        return alertType;
    }

    private Alert.AlertType determineAlertTypeAccordingToThrowable() {
        return throwable != null
               ? Alert.AlertType.ERROR
               : Alert.AlertType.INFORMATION;
    }

    private String determineContentText() {
        return Optional.ofNullable(contentText)
                       .orElseGet(this::retrieveThrowableMessage);
    }

    private String determineId() {
        return Optional.ofNullable(id)
                       .orElseGet(this::determineIdAccordingToAlertType);
    }

    private String determineIdAccordingToAlertType() {
        return Optional.ofNullable(alertType)
                       .map(Alert.AlertType::toString)
                       .map(String::toLowerCase)
                       .orElse("");
    }

    private String determineStyleClass() {
        return Optional.ofNullable(styleClass)
                       .orElse("dialog-pane");
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));

        return writer.toString();
    }

    private String retrieveThrowableMessage() {
        return Optional.ofNullable(throwable)
                       .map(Throwable::getMessage)
                       .orElse("");
    }

    private void setFavIcon(DialogPane dialogPane, URL favIcon) {
        ((Stage) dialogPane.getScene().getWindow())
                .getIcons()
                .add(new Image(favIcon.toString()));
    }

    private void setHeaderIcon(Alert alert, URL icon) {
        alert.setGraphic(createImageView(icon.toString()));
    }

    private void setStylesheet(DialogPane dialogPane, URL stylesheet) {
        dialogPane.getStylesheets()
                  .add(stylesheet.toString());
    }


    @Override
    public Alert build() {
        Alert alert = createAndConfigureAlertInstance();
        configureDialogPane(alert);

        return alert;
    }
}
