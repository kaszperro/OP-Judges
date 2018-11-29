package cs.agh.judges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

public class JudgesParser {

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    public static List<String> getFilePaths(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath), 1)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(f -> getFileExtension(f.toFile()).equals(".json"))
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        }

    }

    public static void parseFiles(List<String> filePaths, JudgementFactory factory) throws ParseException, java.text.ParseException, IOException {
        for (String filePath : filePaths) {
            parseFile(filePath, factory);
        }
    }

    private static void parseFile(String filePath, JudgementFactory factory) throws IOException, ParseException, java.text.ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));

        JSONArray jsonArrayItems = (JSONArray) jsonObject.get("items");


        for (Object item : jsonArrayItems) {
            JSONObject jsonItem = (JSONObject) item;
            int itemId = toIntExact((Long) jsonItem.get("id"));

            Court court = new Court(jsonItem);

            JSONArray jsonArrayCourtCases = (JSONArray) jsonItem.get("courtCases");
            List<CourtCase> courtCases = new LinkedList<>();

            for (Object courtCase : jsonArrayCourtCases) {
                courtCases.add(new CourtCase((JSONObject) courtCase));
            }

            JSONArray jsonArrayJudges = (JSONArray) jsonItem.get("judges");

            Map<Judge, JudgesSpecialRole[]> judgeHashMap = new HashMap<>();

            for (Object objectJudge : jsonArrayJudges) {
                Judge myJudge = new Judge((JSONObject) objectJudge);

                List<JudgesSpecialRole> specialRolesList = new LinkedList<>();
                JSONArray jsonArrayRoles = (JSONArray) ((JSONObject) objectJudge).get("specialRoles");
                for (Object objectRole : jsonArrayRoles) {
                    String roleString = (String) objectRole;
                    specialRolesList.add(JudgesSpecialRole.valueOf(roleString));
                }

                judgeHashMap.put(factory.removeDuplicates(myJudge), specialRolesList.toArray(new JudgesSpecialRole[0]));
            }


            JSONArray jsonArrayReferencedRegulations = (JSONArray) jsonItem.get("referencedRegulations");
            List<Regulation> referencedRegulations = new LinkedList<>();

            for (Object regulationObject : jsonArrayReferencedRegulations) {
                referencedRegulations.add(new Regulation((JSONObject) regulationObject));
            }

            String textContent = (String) jsonItem.get("textContent");

            String judgmentDateString = (String) jsonItem.get("judgmentDate");
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date judgmentDate = format.parse(judgmentDateString);


            List<CourtCase> goodCourtCases = factory.removeDuplicates(courtCases);

            List<Regulation> goodReferencedRegulations = factory.removeDuplicates(referencedRegulations);
            Court goodCourt = factory.removeDuplicates(court);


            Judgement myJudgment = new Judgement(
                    itemId,
                    court,
                    goodCourtCases,
                    judgeHashMap,
                    goodReferencedRegulations,
                    textContent,
                    judgmentDate
            );

            factory.addJudgement(myJudgment);

            factory.addPiecesToJudgement(goodCourtCases.toArray(new CourtCase[0]), myJudgment);
            factory.addPiecesToJudgement(judgeHashMap.keySet().toArray(new Judge[0]), myJudgment);
            factory.addPiecesToJudgement(goodReferencedRegulations.toArray(new Regulation[0]), myJudgment);
            factory.addPiecesToJudgement(goodCourt, myJudgment);
        }
    }

}
