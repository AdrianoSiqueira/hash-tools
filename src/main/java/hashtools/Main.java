package hashtools;

import hashtools.domain.Algorithm;
import hashtools.domain.CheckerRequest;
import hashtools.domain.CheckerResponse;
import hashtools.domain.ComparatorRequest;
import hashtools.domain.ComparatorResponse;
import hashtools.domain.GeneratorRequest;
import hashtools.domain.GeneratorResponse;
import hashtools.operation.Operation;
import hashtools.formatter.CLICheckerResponseFormatter;
import hashtools.formatter.CLIComparatorResponseFormatter;
import hashtools.formatter.CLIGeneratorResponseFormatter;
import hashtools.identification.FileIdentification;
import hashtools.messagedigest.FileUpdater;
import hashtools.officialchecksum.FileOfficialChecksumGetter;
import hashtools.service.Service;
import hashtools.window.ApplicationWindow;
import javafx.application.Application;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        performOperation(new GUIRunnerOperation());
    }

    private static void performOperation(Operation operation) {
        operation.perform();
    }


    private static final class CLIRunnerOperation implements Operation {

        @SuppressWarnings("unused")
        public static final Path
            EMPTY_FILE_HASH = Path.of("empty.sha512"),
            EMPTY_FILE = Path.of("empty.txt"),
            SMALL_FILE = Path.of("/home/adriano/Videos/Star Wars Art This Will Make You Cry.webm"),
            BIG_FILE = Path.of("/home/adriano/Videos/Regular o Cambio Traseiro da Bicicleta.webm"),
            HUGE_FILE = Path.of("/home/adriano/ISOs/Whonix-Xfce-17.2.0.7.ova");


        @Override
        public void perform() {
            Service service = new Service();

            System.out.println(">> Checker");
            runChecker(service);

            System.out.println("\n>> Comparator");
            runComparator(service);

            System.out.println("\n>> Generator");
            runGenerator(service);
        }

        private void runChecker(Service service) {
            CheckerRequest request = new CheckerRequest();
            request.setInput(new FileUpdater(EMPTY_FILE));
            request.setIdentification(new FileIdentification(EMPTY_FILE));
            request.setOfficialChecksumGetter(new FileOfficialChecksumGetter(EMPTY_FILE_HASH));

            CheckerResponse response = service.run(request);
            String result = service.format(response, new CLICheckerResponseFormatter());
            System.out.println(result);
        }

        private void runComparator(Service service) {
            ComparatorRequest request = new ComparatorRequest();
            request.setInput1(new FileUpdater(EMPTY_FILE));
            request.setInput2(new FileUpdater(EMPTY_FILE));
            request.setIdentification1(new FileIdentification(EMPTY_FILE));
            request.setIdentification2(new FileIdentification(EMPTY_FILE));

            ComparatorResponse response = service.run(request);
            String result = service.format(response, new CLIComparatorResponseFormatter());
            System.out.println(result);
        }

        private void runGenerator(Service service) {
            GeneratorRequest request = new GeneratorRequest();
            request.setInput(new FileUpdater(EMPTY_FILE));
            request.setIdentification(new FileIdentification(EMPTY_FILE));
            request.setAlgorithms(List.of(Algorithm.values()));

            GeneratorResponse response = service.run(request);
            String result = service.format(response, new CLIGeneratorResponseFormatter());
            System.out.println(result);
        }
    }

    private static final class GUIRunnerOperation implements Operation {

        @Override
        public void perform() {
            Application.launch(ApplicationWindow.class);
        }
    }
}
