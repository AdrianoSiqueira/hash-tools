package hashtools.gui.window.manual;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
 * @version 1.0.2
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


    private void configureAccordion() {
        accordionSection.expandedPaneProperty()
                        .addListener((observable, oldValue, newValue) -> updateTitledPanesId());
        accordionSection.setExpandedPane(sectionHowToUse);
    }

    @FXML
    private void expandTitlePaneFromLabel(MouseEvent event) {
        if (!(event.getSource() instanceof Label label)) return;
        if (!(label.getUserData() instanceof TitledPane pane)) return;

        accordionSection.setExpandedPane(pane);
    }

    private void linkLeftPaneLabelWithTitlePane() {
        labelHowToUse.setUserData(sectionHowToUse);
        sectionHowToUse.setUserData(labelHowToUse);

        labelSupportedAlgorithms.setUserData(sectionSupportedAlgorithms);
        sectionSupportedAlgorithms.setUserData(labelSupportedAlgorithms);

        labelChecking.setUserData(sectionChecking);
        sectionChecking.setUserData(labelChecking);

        labelGenerating.setUserData(sectionGenerating);
        sectionGenerating.setUserData(labelGenerating);
    }

    private void setLabelIdAccordingToTitlePane(TitledPane pane) {
        Label label = (Label) pane.getUserData();
        label.setId(pane.isExpanded() ? "expanded" : "");
    }

    private void updateTitledPanesId() {
        accordionSection.getPanes()
                        .forEach(this::setLabelIdAccordingToTitlePane);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        linkLeftPaneLabelWithTitlePane();
        configureAccordion();
    }
}
