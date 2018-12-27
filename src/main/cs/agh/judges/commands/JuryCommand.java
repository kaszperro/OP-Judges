package cs.agh.judges.commands;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.judgementElements.Judge;
import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.plots.HistogramDrawer;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class JuryCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "jury";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() > 0)
            throw new RuntimeException("this command doesn't have arguments");

        List<Judgement> judgements = terminalState.judgementDatabase.getJudgements();

        Map<Integer, Integer> occurrences = new HashMap<>();

        for (Judgement judgement : judgements) {
            occurrences.merge(judgement.judgesRoles.size(), 1, (a, b) -> a + b);
        }

        List<Integer> possibleValues = new LinkedList<>(occurrences.keySet());

        return  HistogramDrawer.drawHistogram(occurrences, possibleValues, 60);
    }

    @Override
    public String help() {
        return "outputs ";
    }
}
