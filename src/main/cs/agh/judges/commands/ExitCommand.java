package cs.agh.judges.commands;

import java.util.List;

public class ExitCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        System.exit(0);
        return "the end";
    }

    @Override
    public String help() {
        return "quits program";
    }
}
