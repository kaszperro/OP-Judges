package cs.agh.judges;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JudgesSystem {
    public static void main(String [] args) throws IOException {
        String json = "{" +
                "\"name\":\"Jan Kowalski\", " +
                "\"function\":\"super funkcja\", "+
                "\"specialRoles\":\"[\"PRESIDING_JUDGE\", \"REPORTING_JUDGE\"]\"}";

        Judge jud  = new ObjectMapper()
                .readerFor(Judge.class)
               .readValue(json);
        System.out.println(jud.name);
    }
}
