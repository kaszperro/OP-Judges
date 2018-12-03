package cs.agh.judges.commands;

import cs.agh.judges.CourtType;
import cs.agh.judges.Judgement;
import cs.agh.judges.JudgementFactory;
import cs.agh.judges.plots.HistogramDrawer;

import java.util.LinkedList;
import java.util.List;

public class StatsCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "stats";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() != 1) throw new RuntimeException("stats command need one argument");

        String myArgument = arguments.get(0);

        List<Object> possibleValues = new LinkedList<>();
        List<Object> occurrences = new LinkedList<>();
        List<Judgement> judgements = terminalState.factory.getJudgements();
        switch (myArgument) {
            case "court":
                possibleValues.add(CourtType.ADMINISTRATIVE);
                possibleValues.add(CourtType.SUPREME);
                possibleValues.add(CourtType.COMMON);
                possibleValues.add(CourtType.CONSTITUTIONAL_TRIBUNAL);
                possibleValues.add(CourtType.NATIONAL_APPEAL_CHAMBER);


                for (Judgement judgement : judgements) {
                    occurrences.add(judgement.courtType);
                }

                break;
            case "month":
                for (int i = 1; i <= 12; ++i) {
                    possibleValues.add(i);
                }

                for (Judgement judgement : judgements) {
                    occurrences.add(judgement.judgmentDate.getMonthValue());
                }

                break;
            default:
                throw new RuntimeException(myArgument + " is not recognized");
        }


        return HistogramDrawer.drawHistogram(
                possibleValues,
                occurrences,
                40
        );
    }

    @Override
    public String help() {
        return "displays distributions\nusage: \tstats court displays histogram for courts,\n\tstats month displays histogram for months";
    }
}
