package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.CourtType;
import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.plots.HistogramDrawer;

import java.util.LinkedList;
import java.util.List;

public class CourtsCommand implements ICommand {

    @Override
    public String getCommandName() {
        return "courts";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() > 0)
            throw new RuntimeException("months command doesn't have any arguments");

        List<Object> possibleValues = new LinkedList<>();
        List<Object> occurrences = new LinkedList<>();

        possibleValues.add(CourtType.ADMINISTRATIVE);
        possibleValues.add(CourtType.SUPREME);
        possibleValues.add(CourtType.COMMON);
        possibleValues.add(CourtType.CONSTITUTIONAL_TRIBUNAL);
        possibleValues.add(CourtType.NATIONAL_APPEAL_CHAMBER);

        List<Judgement> judgements = terminalState.judgementDatabase.getJudgements();

        for (Judgement judgement : judgements) {
            occurrences.add(judgement.courtType);
        }

        return HistogramDrawer.drawHistogram(
                possibleValues,
                occurrences,
                40
        );

    }

    @Override
    public String help() {
        return "prints number of cases for different court types\nusage: courts";
    }
}
