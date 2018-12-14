package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.Judgement;

import java.util.List;

public class JudgeCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "judge";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 1)
            throw new RuntimeException("provide one judge name");
        List<Judgement> judgementList = terminalState.judgementDatabase.getJudgeJudgements(arguments.get(0));
        if (judgementList == null) {
            throw new RuntimeException("Judge " + arguments.get(0) + " doesn't exists");
        }
        return Integer.toString(judgementList.size());

    }

    @Override
    public String help() {
        return "Displays number of Judgements for given Judge\nusage: judge \"FirstName LastName\"";
    }
}
