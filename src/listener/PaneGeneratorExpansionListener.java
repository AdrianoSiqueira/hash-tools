package listener;

import controller.AppUI;
import handler.HashCheckerHandler;
import handler.HashGeneratorHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import language.LanguageManager;
import main.Main;

public class PaneGeneratorExpansionListener implements ChangeListener<Boolean> {

    private final AppUI controller;

    public PaneGeneratorExpansionListener(AppUI controller) {
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        Main.resize(500, false);

        controller.setDoneDetails(0.0);

        controller.getField2()
                  .setEditable(!newValue);
        controller.getField2()
                  .clear();

        if (newValue) {
            controller.getButtonRun()
                      .setOnAction(new HashGeneratorHandler(controller));
            controller.getLabelTitle()
                      .setText(LanguageManager.get("Generate.hash.sum"));
            controller.getLabel2()
                      .setText(LanguageManager.get("Destination"));
            controller.getItemSelectNone()
                      .fire();
        } else {
            controller.getButtonRun()
                      .setOnAction(new HashCheckerHandler(controller));
            controller.getLabelTitle()
                      .setText(LanguageManager.get("Check.hash.sum"));
            controller.getLabel2()
                      .setText(LanguageManager.get("Hash"));
        }
    }
}