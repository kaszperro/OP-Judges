package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.judgementElements.Regulation;
import javafx.util.Pair;

import java.util.List;

public class RegulationsCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "regulations";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() > 0)
            throw new RuntimeException("this command doesn't have any arguments");

        List<Pair<Regulation, List<Judgement>>> regulationsPairList = terminalState.judgementDatabase.getRegulationsPairList();

        regulationsPairList.sort((p1, p2) -> p2.getValue().size() - p1.getValue().size());
        StringBuilder ret = new StringBuilder();
        for (int i = 1; i <= Math.min(10, regulationsPairList.size()); ++i) {
            Pair<Regulation, List<Judgement>> judgePair = regulationsPairList.get(i - 1);
            ret.append(i).append(": ").append(judgePair.getKey().toString()).append(": ").append(judgePair.getValue().size()).append("\n");
        }
        return ret.toString();
    }

    @Override
    public String help() {
        return "displays top 10 referenced regulations";
    }
}
