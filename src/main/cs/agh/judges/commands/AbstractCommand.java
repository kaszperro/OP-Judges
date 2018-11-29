package cs.agh.judges.commands;

import java.util.List;

public interface AbstractCommand {
    String getCommandName();

    String run(TerminalState terminalState, List<String> arguments) throws Exception;

    default String help() {
        return "Help not implemented";
    }
}
