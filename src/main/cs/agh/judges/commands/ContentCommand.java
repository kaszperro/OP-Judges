package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.JudgementDatabase;

import java.util.List;

public class ContentCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "content";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.isEmpty()) throw new RuntimeException("need at least one signature");

        JudgementDatabase factory = terminalState.judgementDatabase;

        StringBuilder ret = new StringBuilder();

        for (String signature : arguments) {
            Judgement judgement = factory.getJudgement(signature);
            ret.append("signature: ").append(signature).append("\n").append(judgement.textContent).append("\n----------");
        }
        return ret.toString();

    }

    @Override
    public String help() {
        return "gets justification of judgement";
    }
}
