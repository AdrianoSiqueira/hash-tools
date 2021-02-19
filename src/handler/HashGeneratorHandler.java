package handler;

import aslib.filemanager.FileWriter;
import aslib.fx.util.MessageDialog;
import aslib.security.SHAType;
import controller.AppUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import language.LanguageManager;
import model.Sample;
import model.SharedResources;
import util.HashGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class HashGeneratorHandler implements EventHandler<ActionEvent>,
                                             Callable<Double> {

    private final AppUI controller;

    public HashGeneratorHandler(AppUI controller) {
        this.controller = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        Thread thread = new Thread(controller::setRunningDetails);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            Double call = call();
            controller.setDoneDetails(call);
        }).start();
    }

    @Override
    public Double call() {
        String uri1 = controller.getField1().getText();
        String uri2 = controller.getField2().getText();

        if (uri1.isEmpty() || uri2.isEmpty()) {
            return 0.0;
        }

        Object object = Paths.get(uri1);
        Path   path   = Paths.get(uri2);

        final List<Sample> sampleList = SharedResources.INSTANCE.getSampleList();
        sampleList.clear();

        List<SHAType> shaList = new ArrayList<>();
        controller.getCheckBoxList()
                  .forEach(checkBox -> {
                      if (checkBox.isSelected()) {
                          shaList.add((SHAType) checkBox.getUserData());
                      }
                  });

        CountDownLatch latch = new CountDownLatch(shaList.size());

        shaList.forEach(shaType -> {
            Sample sample = new Sample(object, shaType);

            SharedResources.INSTANCE
                    .getExecutorService()
                    .submit(new HashGenerator(sample, latch));

            sampleList.add(sample);
        });

        try {
            latch.await();
            Files.deleteIfExists(path);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        FileWriter writer = new FileWriter();

        sampleList.sort(Comparator.comparingInt(sample -> sample.getShaType().getLength()));
        sampleList.forEach(sample -> {
            StringBuilder content =
                    new StringBuilder(sample.getOfficialHash());

            if (sample.isUsingFile()) {
                content.append("  ")
                       .append(((Path) sample.getObject()).getFileName());
            }

            content.append(System.lineSeparator());
            writer.append(content.toString(), path);
        });

        Platform.runLater(() -> new MessageDialog.Builder()
                .alertType(Alert.AlertType.INFORMATION)
                .title(LanguageManager.get("HashTools"))
                .header(LanguageManager.get("Generate.hash.sum"))
                .content(LanguageManager.get("Task.done."))
                .create()
                .show()
        );

        return 1.0;
    }
}