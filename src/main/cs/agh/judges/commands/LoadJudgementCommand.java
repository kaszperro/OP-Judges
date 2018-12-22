package cs.agh.judges.commands;

import java.util.List;

public class LoadJudgementCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "load";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 1)
            throw new RuntimeException("This command need one argument with path to files");


        int judgementsCount = terminalState.judgementDatabase.judgements.size();


        terminalState.getParserContainer().parseDirectory(arguments.get(0), terminalState.judgementDatabase);


        return (terminalState.judgementDatabase.judgements.size() - judgementsCount) + " judgements";
    }

    @Override
    public String help() {
        return "Loads and parses files\n usage: load [path directory or file]";
    }
}
