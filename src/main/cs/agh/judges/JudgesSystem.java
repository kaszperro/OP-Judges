package cs.agh.judges;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class JudgesSystem {
    public static void main(String[] args) {


        JsonParser parser = new JsonParser();
        try {
            parser.parseFile("Resources/judgments.json");
        } catch (ParseException e) {
            System.out.println("Parse problem");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO problem");
            e.printStackTrace();
        }

    }
}
