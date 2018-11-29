package cs.agh.judges.commands;

import java.util.List;

public class ExplanationCommand implements AbstractCommand {
    @Override
    public String getCommandName() {
        return "explain";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        return null;
    }

    @Override
    public String help() {
        return "gets justification of judgement";
    }
}
