package cs.agh.judges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import static java.lang.Math.toIntExact;

public class JudgementDatabase {
    public Set<Judgement> judgements = new HashSet<>();


    private Map<Judge, List<Judgement>> judgesListMap = new HashMap<>();
    private Map<CourtCase, Judgement> courtCasesMap = new HashMap<>();
    private Map<Regulation, List<Judgement>> regulationListMap = new HashMap<>();
    private EnumMap<CourtType, List<Judgement>> courtTypeListMap = new EnumMap<>(CourtType.class);


    public void addJudgement(Judgement judgement) {
        judgements.add(judgement);
    }


    public Map<Judge, JudgesSpecialRole[]> createJudges(JSONArray jsonArrayJudges) {

        Map<Judge, JudgesSpecialRole[]> judgeHashMap = new HashMap<>();

        for (Object objectJudge : jsonArrayJudges) {
            Judge myJudge = new Judge((String) ((JSONObject) objectJudge).get("name"));

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

            Regulation myRegulation = new Regulation(
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
            CourtCase myCourtCase = new CourtCase((String) ((JSONObject) courtCase).get("caseNumber"));
            courtCases.add(myCourtCase);
        }
        return courtCases;
    }


    public Judgement getJudgement(String courtCase) {
        CourtCase myCourtCase = new CourtCase(courtCase);
        return courtCasesMap.get(myCourtCase);

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


    public List<Regulation> getRegulations() {
        return new LinkedList<>(regulationListMap.keySet());
    }


    public List<Judgement> getJudgements() {
        return new LinkedList<>(judgements);
    }



}
