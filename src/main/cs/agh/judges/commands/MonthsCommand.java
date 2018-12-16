package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.plots.HistogramDrawer;

import java.util.LinkedList;
import java.util.List;

public class MonthsCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "months";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() > 0)
            throw new RuntimeException("months command doesn't have any arguments");

        List<Object> possibleValues = new LinkedList<>();
        List<Object> occurrences = new LinkedList<>();

        List<Judgement> judgements = terminalState.judgementDatabase.getJudgements();

        for (int i = 1; i <= 12; ++i) {
            possibleValues.add(i);
        }

        for (Judgement judgement : judgements) {
            occurrences.add(judgement.judgmentDate.getMonthValue());
        }

        return HistogramDrawer.drawHistogram(
                possibleValues,
                occurrences,
                40
        );

    }

    @Override
    public String help() {
        return "prints number of judgements in following months\nusage: months";
    }
}
