package cs.agh.judges;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Math.toIntExact;

public class JsonParser {


    public void parseFile(String filePath) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filePath));

        JSONArray items = (JSONArray) jsonObject.get("items");

        for (Object item : items) {
            JSONObject jsonItem = (JSONObject) item;
            int itemId = toIntExact((Long) jsonItem.get("id"));
            System.out.println(itemId);

            CourtTypes courtType = CourtTypes.valueOf((String) jsonItem.get("courtType"));
            System.out.println(courtType.toString());
        }
    }

}
