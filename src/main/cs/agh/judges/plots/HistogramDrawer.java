package cs.agh.judges.plots;


import com.mitchtalmadge.asciidata.graph.ASCIIGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistogramDrawer {

    public static <T extends Object> String drawHistogram(Map<T, Integer> occurrences, List<T> possibleValues, int maxWidth) {
        int maxValue = 0;

        for (Object obj : possibleValues) {
            maxValue = Math.max(maxValue, occurrences.get(obj));
        }


        StringBuilder retString = new StringBuilder();


        for (Object myObj : possibleValues) {
            int myOcc = occurrences.get(myObj);
            int myHeight = myOcc * maxWidth / maxValue;

            retString.append(myObj.toString()).append(":\t\t");
            for (int j = 0; j < myHeight; ++j) {
                retString.append("#");
            }

            retString.append("  ").append(myOcc).append("\n");
        }


        return retString.toString();
    }

    public static <T extends Object> String drawHistogram(List<T> possibleValues, List<T> allObjects, int maxWidth) {

        Map<T, Integer> occurrences = new HashMap<>();

        for (T obj : possibleValues) {
            occurrences.put(obj, 0);
        }

        for (T obj : allObjects) {
            occurrences.merge(obj, 1, (a, b) -> a + b);
        }


        int maxValue = 0;

        for (Object obj : possibleValues) {
            maxValue = Math.max(maxValue, occurrences.get(obj));
        }


        return drawHistogram(occurrences, possibleValues, maxWidth);

    }

}
