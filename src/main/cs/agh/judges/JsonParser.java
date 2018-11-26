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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.toIntExact;

public class JsonParser {

    public static List<String> getFilePaths(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(Path::toAbsolutePath)
                    .map(Path::toString)

                    .collect(Collectors.toList());
        }

    }

    public static void parseFiles(String[] filePaths, JudgementFactory factory) throws ParseException, java.text.ParseException, IOException {
        for (String filePath : filePaths) {
            parseFile(filePath, factory);
        }
    }

    public static void parseFile(String filePath, JudgementFactory factory) throws IOException, ParseException, java.text.ParseException {
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

            List<Judge> judges = new LinkedList<>();

            for (Object objectJudge : jsonArrayJudges) {
                judges.add(new Judge((JSONObject) objectJudge));
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
            List<Judge> goodJudges = factory.removeDuplicates(judges);
            List<Regulation> goodReferencedRegulations = factory.removeDuplicates(referencedRegulations);
            Court goodCourt = factory.removeDuplicates(court);


            Judgement myJudgment = new Judgement(
                    itemId,
                    court,
                    goodCourtCases,
                    goodJudges,
                    goodReferencedRegulations,
                    textContent,
                    judgmentDate
            );

            factory.addJudgement(myJudgment);

            factory.addPiecesToJudgement(goodCourtCases, myJudgment);
            factory.addPiecesToJudgement(goodJudges, myJudgment);
            factory.addPiecesToJudgement(goodReferencedRegulations, myJudgment);
            factory.addPiecesToJudgement(goodCourt, myJudgment);
        }
    }

}
