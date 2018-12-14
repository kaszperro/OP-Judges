package cs.agh.judges;

import cs.agh.judges.judgementElements.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public static void parseFiles(List<String> filePaths, JudgementDatabase factory) throws ParseException, java.text.ParseException, IOException {
        for (String filePath : filePaths) {
            parseFile(filePath, factory);
        }
    }

    private static void parseFile(String filePath, JudgementDatabase factory) throws IOException, ParseException, java.text.ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));

        JSONArray jsonArrayItems = (JSONArray) jsonObject.get("items");


        for (Object item : jsonArrayItems) {
            JSONObject jsonItem = (JSONObject) item;
            int itemId = toIntExact((Long) jsonItem.get("id"));

            CourtType courtType = CourtType.valueOf((String) jsonItem.get("courtType"));

            List<CourtCase> courtCases = factory.createCourtCases((JSONArray) jsonItem.get("courtCases"));

            Map<Judge, JudgesSpecialRole[]> judgeHashMap = factory.createJudges((JSONArray) jsonItem.get("judges"));
            List<Regulation> referencedRegulations = factory.createRegulations((JSONArray) jsonItem.get("referencedRegulations"));

            String textContent = (String) jsonItem.get("textContent");



            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //formatter = formatter.withLocale( Locale. );
            LocalDate judgmentDate = LocalDate.parse((String) jsonItem.get("judgmentDate"), formatter);



            Judgement myJudgment = new Judgement(
                    itemId,
                    courtType,
                    courtCases,
                    judgeHashMap,
                    referencedRegulations,
                    textContent,
                    judgmentDate
            );

            factory.addJudgement(myJudgment);
            factory.addJudgementToJudges(judgeHashMap.keySet().toArray(new Judge[0]), myJudgment);
            factory.addJudgementToCourtCases(courtCases.toArray(new CourtCase[0]), myJudgment);
            factory.addJudgementToCourtType(courtType, myJudgment);
            factory.addJudgementToRegulations(referencedRegulations.toArray(new Regulation[0]), myJudgment);
        }
    }

}
