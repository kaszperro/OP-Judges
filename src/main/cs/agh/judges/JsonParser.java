package cs.agh.judges;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.toIntExact;

public class JsonParser {


    public void parseFile(String filePath, JudgementFactory factory) throws IOException, ParseException, java.text.ParseException {
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

            String judgmentDateString = (String) jsonItem.get("judgmentDate");
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            Date jugmentDate = format.parse(judgmentDateString);

            Judgement myJudgment = new Judgement();

            factory.addJudgement(myJudgment);

            List<CourtCase> goodCourtCases = factory.removeDuplicats(courtCases);
            List<Judge> goodJudges = factory.removeDuplicats(judges);
            List<Regulation> goodReferencedRegulations = factory.removeDuplicats(referencedRegulations);

            factory.addPiecesToJudgement(goodCourtCases, myJudgment);
            factory.addPiecesToJudgement(goodJudges, myJudgment);
            factory.addPiecesToJudgement(goodReferencedRegulations, myJudgment);

        }
    }

}
