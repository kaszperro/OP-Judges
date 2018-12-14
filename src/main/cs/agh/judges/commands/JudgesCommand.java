package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.Judge;
import cs.agh.judges.judgementElements.Judgement;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.List;

public class JudgesCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "judges";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 0)
            throw new RuntimeException("Judges command doesn't have any arguments");

        List<Pair<Judge, List<Judgement>>> judgesPairList = terminalState.judgementDatabase.getJudgesPairList();
        judgesPairList.sort((p1, p2) -> p2.getValue().size() - p1.getValue().size());
        StringBuilder ret = new StringBuilder();
        for (int i = 1; i <= Math.min(10, judgesPairList.size()); ++i) {
            Pair<Judge, List<Judgement>> judgePair = judgesPairList.get(i - 1);
            ret.append(i).append(": ").append(judgePair.getKey().toString()).append(": ").append(judgePair.getValue().size()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String help() {
        return "Displays top 10 Judges";
    }
}
