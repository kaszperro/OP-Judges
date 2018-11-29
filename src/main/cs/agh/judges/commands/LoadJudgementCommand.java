package cs.agh.judges.commands;

import cs.agh.judges.JudgesParser;

import java.util.List;

public class LoadJudgementCommand implements AbstractCommand {
    @Override
    public String getCommandName() {
        return "load";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.isEmpty())
            throw new RuntimeException("Need at least one parameter");

        boolean readDirectory = false;
        if (arguments.get(0).equals("-d")) {
            readDirectory = true;
        }

        if ((!readDirectory && arguments.isEmpty()) || (readDirectory && arguments.size() == 1))
            throw new RuntimeException("Need at least one json path");

        int judgementsCount = terminalState.factory.judgements.size();


        List<String> filesPaths;
        if (readDirectory) {
            filesPaths = JudgesParser.getFilePaths(arguments.get(1));
        } else {
            filesPaths = arguments;
        }
        JudgesParser.parseFiles(filesPaths, terminalState.factory);


        return "Loaded " + filesPaths.size() + " json files, " +
                (terminalState.factory.judgements.size() - judgementsCount) + " judgements";
    }

    @Override
    public String help() {
        return "Loads and parses files\n to load many files: load [path1] [path2] ...\n to load all files from directory: load -d [directory path]";
    }
}
