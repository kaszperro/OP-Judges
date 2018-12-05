package cs.agh.judges.plots;


import com.mitchtalmadge.asciidata.graph.ASCIIGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramDrawer {

    public static String drawHistogram(List<Object> possibleValues, List<Object> allObjects, int maxWidth) {

        Map<Object, Integer> occurrences = new HashMap<>();

        for (Object obj : possibleValues) {
            occurrences.put(obj, 0);
        }

        for (Object obj : allObjects) {
            occurrences.merge(obj, 1, (a, b) -> a + b);
        }


        int maxValue = 0;

        for (Object obj : possibleValues) {
            maxValue = Math.max(maxValue, occurrences.get(obj));
        }


        StringBuilder retString = new StringBuilder();


        for (Object myObj : possibleValues) {
            int myOcc = occurrences.get(myObj);
            int myHeight = myOcc * maxWidth / maxValue;

            retString.append(myObj.toString()).append(":\t");
            for (int j = 0; j < myHeight; ++j) {
                retString.append("#");
            }

            retString.append("  ").append(myOcc).append("\n");
        }


        return retString.toString();

    }

}
