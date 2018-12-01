package cs.agh.judges.commands;

import java.util.List;

public class PwdCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "pwd";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) {
        return terminalState.currentPath;
    }

    @Override
    public String help() {
        return "Shows current directory location";
    }
}
