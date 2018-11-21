package cs.agh.judges;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class JudgesSystem {
    public static void main(String [] args)  {




        JSONParser jsonParser = new JSONParser();
        Object object;

        try {

            object = jsonParser.parse(new FileReader("Resources/Example.json"));
            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");
            System.out.println("Name: " + name);

            String function = (String) jsonObject.get("function");
            System.out.println("Function: " + function);


            JSONArray specialRoles = (JSONArray) jsonObject.get("specialRoles");

            for (Object specialRole : specialRoles) {

                String role = (String) specialRole;
                System.out.println("Rola " + role);
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
