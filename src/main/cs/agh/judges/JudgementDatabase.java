package cs.agh.judges;

import cs.agh.judges.judgementElements.*;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class JudgementDatabase {
    public Set<Judgement> judgements = new HashSet<>();


    private Map<Judge, List<Judgement>> judgesListMap = new HashMap<>();
    private Map<CourtCase, Judgement> courtCasesMap = new HashMap<>();
    private Map<Regulation, List<Judgement>> regulationListMap = new HashMap<>();
    private EnumMap<CourtType, List<Judgement>> courtTypeListMap = new EnumMap<>(CourtType.class);


    public void addJudgement(Judgement judgement) {
        judgements.add(judgement);
    }


    public Judgement getJudgement(String courtCase) {
        return courtCasesMap.get(new CourtCase(courtCase));
    }


    public void addJudgementToJudges(Judge[] judges, Judgement judgement) {
        for (Judge judge : judges) {
            judgesListMap.putIfAbsent(judge, new LinkedList<>());
            judgesListMap.get(judge).add(judgement);


        }
    }

    public void addJudgementToRegulations(Regulation[] regulations, Judgement judgement) {
        for (Regulation regulation : regulations) {
            regulationListMap.putIfAbsent(regulation, new LinkedList<>());
            regulationListMap.get(regulation).add(judgement);

        }
    }

    public void addJudgementToCourtCases(CourtCase[] courtCases, Judgement judgement) {
        for (CourtCase courtCase : courtCases) {
            courtCasesMap.putIfAbsent(courtCase, judgement);
        }
    }

    public void addJudgementToCourtType(CourtType courtType, Judgement judgement) {
        courtTypeListMap.putIfAbsent(courtType, new LinkedList<>());
        courtTypeListMap.get(courtType).add(judgement);

    }


    public List<Judge> getJudges() {
        return new LinkedList<>(judgesListMap.keySet());
    }


    public List<Judgement> getJudgements() {
        return new LinkedList<>(judgements);
    }

    public List<Pair<Regulation, List<Judgement>>> getRegulationsPairList() {
        return regulationListMap.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public List<Pair<Judge, List<Judgement>>> getJudgesPairList() {
        return judgesListMap.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


    public List<Judgement> getJudgeJudgements(String name) {
        return judgesListMap.get(new Judge(name));
    }
}
