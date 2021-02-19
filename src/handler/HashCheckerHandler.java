package handler;

import aslib.filemanager.FileReader;
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
import util.HashChecker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class HashCheckerHandler implements EventHandler<ActionEvent>,
                                           Callable<Double> {

    private final AppUI controller;

    public HashCheckerHandler(AppUI controller) {
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
            Double reliability = call();
            controller.setDoneDetails(reliability);
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

        if (Files.isRegularFile(path)) {
            FileReader reader = new FileReader();

            /*
             * The Optional returned by FileReader can be ignored, because it
             * was checked already in if. There is no way the Optional is absent.
             */
            //noinspection OptionalGetWithoutIsPresent
            List<String> lines = reader.readLines(path)
                                       .get();

            lines.forEach(line -> {
                String[] split = line.split(" ");

                for (String s : split) {
                    SHAType.getByLength(s.length())
                           .ifPresent(shaType -> {
                               Sample sample = new Sample(object, shaType, s);
                               sampleList.add(sample);
                           });
                }
            });
        } else {
            SHAType.getByLength(uri2.length())
                   .ifPresent(shaType -> {
                       Sample sample = new Sample(object, shaType, uri2);
                       sampleList.add(sample);
                   });
        }

        CountDownLatch latch = new CountDownLatch(sampleList.size());

        sampleList.forEach(sample -> SharedResources.INSTANCE
                .getExecutorService()
                .submit(new HashChecker(sample, latch)));

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> new MessageDialog.Builder()
                .alertType(Alert.AlertType.INFORMATION)
                .title(LanguageManager.get("HashTools"))
                .header(LanguageManager.get("Check.hash.sum"))
                .content(LanguageManager.get("Task.done..The.bar.indicates.the.reliability.level."))
                .create()
                .show());

        return Sample.calculateReliabilityPercentage(sampleList) / 100;
    }
}