package cs.agh.judges;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListCommand implements AbstractCommand {

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public String run(TerminalState state, List<String> arguments) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(state.currentPath), 1)) {
            List<String> listCommand = paths
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            StringBuilder ret = new StringBuilder();
            for (String myDir : listCommand) {
                ret.append(myDir);
                ret.append("\n");
            }
            return ret.toString();
        }
    }

    @Override
    public String help() {
        return "Lists files in current directory";
    }
}
