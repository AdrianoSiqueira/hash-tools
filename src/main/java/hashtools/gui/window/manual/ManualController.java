package hashtools.gui.window.manual;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>
 * Manual screen controller class.
 * </p>
 *
 * @author Adriano Siqueira
 * @version 1.0.1
 * @since 1.0.0
 */
public class ManualController implements Initializable {

    @FXML private HBox paneRoot;
    @FXML private VBox leftPane;

    @FXML private Accordion  accordionSection;
    @FXML private TitledPane sectionHowToUse;
    @FXML private TitledPane sectionSupportedAlgorithms;
    @FXML private TitledPane sectionChecking;
    @FXML private TitledPane sectionGenerating;

    @FXML private Label labelHowToUse;
    @FXML private Label labelSupportedAlgorithms;
    @FXML private Label labelChecking;
    @FXML private Label labelGenerating;
    @FXML private Label labelContentHowToUse;
    @FXML private Label labelContentSupportedAlgorithms;
    @FXML private Label labelContentChecking;
    @FXML private Label labelContentGenerating;


    private void addListenerOnAccordion() {
        accordionSection.expandedPaneProperty()
                        .addListener((observable, oldValue, newValue) -> updateTitledPanesId());
    }

    private void configureLeftPaneLabels() {
        LeftPanelLabelMouseClickHandler handler = new LeftPanelLabelMouseClickHandler();

        labelHowToUse.setUserData(sectionHowToUse);
        labelSupportedAlgorithms.setUserData(sectionSupportedAlgorithms);
        labelChecking.setUserData(sectionChecking);
        labelGenerating.setUserData(sectionGenerating);

        leftPane.getChildren().forEach(n -> n.setOnMouseClicked(handler));
    }

    private void configureTitlePanes() {
        sectionHowToUse.setUserData(labelHowToUse);
        sectionSupportedAlgorithms.setUserData(labelSupportedAlgorithms);
        sectionChecking.setUserData(labelChecking);
        sectionGenerating.setUserData(labelGenerating);
    }

    private void updateTitledPanesId() {
        accordionSection.getPanes()
                        .forEach(pane -> {
                            String id = pane.isExpanded() ? "expanded" : "";

                            pane.setId(id);
                            ((Node) pane.getUserData()).setId(id);
                        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListenerOnAccordion();
        configureLeftPaneLabels();
        configureTitlePanes();
        updateTitledPanesId();
    }


    private class LeftPanelLabelMouseClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Node       source  = (Node) event.getSource();
            TitledPane section = (TitledPane) source.getUserData();

            accordionSection.setExpandedPane(section);
        }
    }
}
