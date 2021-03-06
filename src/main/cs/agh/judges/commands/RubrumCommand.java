package cs.agh.judges.commands;

import cs.agh.judges.judgementElements.CourtType;
import cs.agh.judges.judgementElements.Judge;
import cs.agh.judges.judgementElements.Judgement;
import cs.agh.judges.judgementElements.JudgesSpecialRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RubrumCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "rubrum";
    }

    @Override
    public String run(TerminalState terminalState, List<String> arguments) throws Exception {
        if (arguments.size() == 0)
            throw new RuntimeException("need signature");
        StringBuilder response =
                new StringBuilder();
        int counter = 0;
        for (String signature : arguments) {
            Judgement judgement = terminalState.judgementDatabase.getJudgement(signature);

            if (judgement == null) {
                throw new RuntimeException("didn't find signature: " + signature);
            }


            CourtType courtType = judgement.courtType;
            LocalDate judgementDate = judgement.judgmentDate;

            StringBuilder myRubrum =
                    new StringBuilder("Signature: " + signature + "\n" +
                            "Date: " + judgementDate.toString() + "\n" +
                            "Court Type: " + courtType.toString() + "\n" +
                            "Judges:" + "\n");

            for (Map.Entry<Judge, JudgesSpecialRole[]> judgeEntry : judgement.judgesRoles.entrySet()) {
                myRubrum.append("\t").append(judgeEntry.getKey().toString()).append(":\n");

                for (JudgesSpecialRole specialRole : judgeEntry.getValue()) {
                    myRubrum.append("\t\t").append(specialRole.toString()).append("\n");
                }
            }
            if (counter + 1 < arguments.size())
                myRubrum.append("--------------\n");
            response.append(myRubrum);

            counter++;
        }


        return response.toString();
    }

    @Override
    public String help() {
        return "outputs metrics for signatures\nusage: rubrum \"signature1\" \"signature2\"...";
    }
}
