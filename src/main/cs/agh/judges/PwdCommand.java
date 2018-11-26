package cs.agh.judges;

import java.util.List;

public class PwdCommand implements AbstractCommand {
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
