package cs.agh.judges.commands;

import cs.agh.judges.JudgesParser;

import java.io.IOException;
import java.util.List;

public class ListCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "list";
    }

    @Override
    public String run(TerminalState state, List<String> arguments) throws IOException {
        String currentPath = state.currentPath;
        if (arguments.size() > 0) {
            currentPath = arguments.get(0);
        }
        List<String> listCommand = JudgesParser.getFilePaths(currentPath);
        StringBuilder ret = new StringBuilder();
        for (String myDir : listCommand) {
            ret.append(myDir);
            ret.append("\n");
        }
        return ret.toString();

    }

    @Override
    public String help() {
        return "Lists JSON files in directory\nto list current directory: list\nto list some directory: list [directory name]";
    }
}
