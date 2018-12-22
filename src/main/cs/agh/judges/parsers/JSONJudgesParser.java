package cs.agh.judges.parsers;

import cs.agh.judges.JudgementDatabase;
import cs.agh.judges.judgementElements.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class JSONJudgesParser implements IJudgesParser {
    @Override
    public String getSupportedExtension() {
        return ".json";
    }

    @Override
    public void parseFile(String filePath, JudgementDatabase judgementDatabase) {

        JSONParser jsonParser = new JSONParser();
        try (Reader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray jsonArrayItems = (JSONArray) jsonObject.get("items");

            for (Object item : jsonArrayItems) {
                JSONObject jsonItem = (JSONObject) item;

                CourtType courtType = CourtType.valueOf((String) jsonItem.get("courtType"));

                List<CourtCase> courtCases = createCourtCases((JSONArray) jsonItem.get("courtCases"));

                Map<Judge, JudgesSpecialRole[]> judgeHashMap = createJudges((JSONArray) jsonItem.get("judges"));

                List<AbstractRegulation> referencedRegulations = createRegulations((JSONArray) jsonItem.get("referencedRegulations"));

                String textContent = (String) jsonItem.get("textContent");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate judgmentDate = LocalDate.parse((String) jsonItem.get("judgmentDate"), formatter);


                Judgement myJudgment = new Judgement(
                        courtType,
                        courtCases,
                        judgeHashMap,
                        referencedRegulations,
                        textContent,
                        judgmentDate
                );

                judgementDatabase.addJudgement(myJudgment);
                judgementDatabase.addJudgementToJudges(judgeHashMap.keySet().toArray(new Judge[0]), myJudgment);
                judgementDatabase.addJudgementToCourtCases(courtCases.toArray(new CourtCase[0]), myJudgment);
                judgementDatabase.addJudgementToCourtType(courtType, myJudgment);
                judgementDatabase.addJudgementToRegulations(referencedRegulations.toArray(new AbstractRegulation[0]), myJudgment);

            }

        } catch (ParseException | IOException e) {
            System.out.println("error while parsing " + filePath);
        }


    }


    private Map<Judge, JudgesSpecialRole[]> createJudges(JSONArray jsonArrayJudges) {

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

    private List<CourtCase> createCourtCases(JSONArray jsonArrayCourtCases) {
        List<CourtCase> courtCases = new LinkedList<>();

        for (Object courtCase : jsonArrayCourtCases) {
            CourtCase myCourtCase = new CourtCase((String) ((JSONObject) courtCase).get("caseNumber"));
            courtCases.add(myCourtCase);
        }
        return courtCases;
    }

    private List<AbstractRegulation> createRegulations(JSONArray jsonArrayReferencedRegulations) {
        List<AbstractRegulation> referencedRegulations = new LinkedList<>();

        for (Object regulationObject : jsonArrayReferencedRegulations) {
            JSONObject JSONRegulation = (JSONObject) regulationObject;

            String journalTitle = (String) JSONRegulation.get("journalTitle");
            int journalNo = toIntExact((Long) JSONRegulation.get("journalNo"));
            int journalYear = toIntExact((Long) JSONRegulation.get("journalYear"));
            int journalEntry = toIntExact((Long) JSONRegulation.get("journalEntry"));

            JSONRegulation myRegulation = new JSONRegulation(
                    journalYear,
                    journalEntry,
                    journalNo,
                    journalTitle
            );

            referencedRegulations.add(myRegulation);
        }
        return referencedRegulations;
    }
}
