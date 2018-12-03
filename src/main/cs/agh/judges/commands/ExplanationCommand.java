package cs.agh.judges.commands;

import cs.agh.judges.CourtCase;
import cs.agh.judges.Judgement;
import cs.agh.judges.JudgementFactory;

import java.util.List;

public class ExplanationCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "explain";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.isEmpty()) throw new RuntimeException("need at least one signature");

        JudgementFactory factory = terminalState.factory;

        StringBuilder ret = new StringBuilder();

        for (String signature : arguments) {
            Judgement judgement = factory.getJudgement(new CourtCase(signature));
            ret.append("signature: ").append(signature).append("\n").append(judgement.textContent).append("\n----------");
        }
        return ret.toString();

    }

    @Override
    public String help() {
        return "gets justification of judgement";
    }
}
