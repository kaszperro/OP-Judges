package cs.agh.judges.commands;

import java.util.List;

public class HelpCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        StringBuilder ret = new StringBuilder();

        for (ICommand command : terminalState.possibleCommands) {
            ret.append("Command: ").append(command.getCommandName()).append("\n").append(command.help()).append("\n----------\n");
        }

        return ret.toString();
    }

    @Override
    public String help() {
        return "displays all commands";
    }
}
