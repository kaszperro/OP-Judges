package cs.agh.judges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static java.lang.Math.toIntExact;

public class JudgementFactory {
    public Set<Judgement> judgements = new HashSet<>();

    private Map<IJudgementElement, IJudgementElement> judgementElements = new HashMap<>();


    private Set<Judge> judgesSet = new HashSet<>();
    private Set<Regulation> regulationsSet = new HashSet<>();
    private Set<CourtCase> courtCasesSet = new HashSet<>();
    private Set<CourtType> courtTypesSet = new HashSet<>();


    public void addJudgement(Judgement judgement) {
        judgements.add(judgement);
    }


    private Judge createJudge(String name) {
        Judge myJudge = new Judge(name);
        judgementElements.putIfAbsent(myJudge, myJudge);
        return (Judge) judgementElements.get(myJudge);
    }

    private Regulation createRegulation(int journalYear, int journalEntry, int journalNo, String journalTitle) {
        Regulation myRegulation = new Regulation(journalYear, journalEntry, journalNo, journalTitle);
        judgementElements.putIfAbsent(myRegulation, myRegulation);
        return (Regulation) judgementElements.get(myRegulation);
    }

    private CourtCase createCourtCase(String caseNumber) {
        CourtCase myCourtCase = new CourtCase(caseNumber);
        judgementElements.putIfAbsent(myCourtCase, myCourtCase);
        return (CourtCase) judgementElements.get(myCourtCase);
    }


    public Map<Judge, JudgesSpecialRole[]> createJudges(JSONArray jsonArrayJudges) {

        Map<Judge, JudgesSpecialRole[]> judgeHashMap = new HashMap<>();

        for (Object objectJudge : jsonArrayJudges) {
            Judge myJudge = createJudge((String) ((JSONObject) objectJudge).get("name"));

            List<JudgesSpecialRole> specialRolesList = new LinkedList<>();
            JSONArray jsonArrayRoles = (JSONArray) ((JSONObject) objectJudge).get("specialRoles");
            for (Object objectRole : jsonArrayRoles) {
                String roleString = (String) objectRole;
                specialRolesList.add(JudgesSpecialRole.valueOf(roleString));
            }

            judgeHashMap.put(myJudge, specialRolesList.toArray(new JudgesSpecialRole[0]));
        }
        return judgeHashMap;
    }

    public List<Regulation> createRegulations(JSONArray jsonArrayReferencedRegulations) {
        List<Regulation> referencedRegulations = new LinkedList<>();

        for (Object regulationObject : jsonArrayReferencedRegulations) {
            JSONObject JSONRegulation = (JSONObject) regulationObject;

            String journalTitle = (String) JSONRegulation.get("journalTitle");
            int journalNo = toIntExact((Long) JSONRegulation.get("journalNo"));
            int journalYear = toIntExact((Long) JSONRegulation.get("journalYear"));
            int journalEntry = toIntExact((Long) JSONRegulation.get("journalEntry"));

            Regulation myRegulation = createRegulation(
                    journalYear,
                    journalEntry,
                    journalNo,
                    journalTitle
            );

            referencedRegulations.add(myRegulation);
        }
        return referencedRegulations;
    }

    public List<CourtCase> createCourtCases(JSONArray jsonArrayCourtCases) {
        List<CourtCase> courtCases = new LinkedList<>();

        for (Object courtCase : jsonArrayCourtCases) {
            CourtCase myCourtCase = createCourtCase((String) ((JSONObject) courtCase).get("caseNumber"));
            courtCases.add(myCourtCase);
        }
        return courtCases;
    }


    public Judgement getJudgement(CourtCase courtCase) {
        courtCase = (CourtCase) judgementElements.get(courtCase);
        if (courtCase == null) return null;
        return courtCase.getJudgementList().get(0);
    }


    public void addJudgementToJudges(Judge[] judges, Judgement judgement) {
        for (Judge judge : judges) {
            judge.addJudgement(judgement);
            judgesSet.add(judge);

        }
    }

    public void addJudgementToRegulations(Regulation[] regulations, Judgement judgement) {
        for (Regulation regulation : regulations) {
            regulation.addJudgement(judgement);
            regulationsSet.add(regulation);
        }
    }

    public void addJudgementToCourtCases(CourtCase[] courtCases, Judgement judgement) {
        for (CourtCase courtCase : courtCases) {
            courtCase.addJudgement(judgement);
            courtCasesSet.add(courtCase);

        }
    }

    public void addJudgementToCourtType(CourtType courtType, Judgement judgement) {
        courtType.addJudgement(judgement);
        courtTypesSet.add(courtType);
    }


    public List<Judge> getJudges() {
        return new LinkedList<>(judgesSet);
    }


    public List<Regulation> getRegulations() {
        return new LinkedList<>(regulationsSet);
    }


    public List<Judgement> getJudgements() {
        return new LinkedList<>(judgements);
    }

}
