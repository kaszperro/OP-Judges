package cs.agh.judges.commands;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.judgementElements.Judge;
import cs.agh.judges.judgementElements.Judgement;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

        List<Pair<Integer, Integer>> sortedOccurrences = occurrences.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue() - e1.getValue())
                .map(e -> new Pair<>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());


        StringBuilder builder = new StringBuilder();
        for (Pair<Integer, Integer> myPair : sortedOccurrences) {
            builder.append("Number of Judges: ").append(myPair.getKey()).
                    append(", occurrences:").append(myPair.getValue()).append("\n");
        }

        return builder.toString();
    }

    @Override
    public String help() {
        return "outputs ";
    }
}
