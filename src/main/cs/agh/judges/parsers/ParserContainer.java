package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParserContainer {
    private List<IJudgesParser> availableParsers = new LinkedList<>();


    public void addParser(IJudgesParser parser) {
        availableParsers.add(parser);
    }

    public void parseFile(String filePath, JudgementDatabase judgementDatabase) {
        for (IJudgesParser parser : availableParsers) {
            if (filePath.endsWith(parser.getSupportedExtension())) {
                parser.parseFile(filePath, judgementDatabase);
                break;
            }
        }
    }

    public void parseDirectory(String directoryPath, JudgementDatabase judgementDatabase) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .forEach(f -> parseFile(f, judgementDatabase));

        }
    }
}
