package cs.agh.judges;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Judge implements IJudgementElement {
    private final List<Judgement> judgementsList = new LinkedList<>();
    public String name;
    public String function;


    public Judge(JSONObject object) {

        name = (String) object.get("name");
        function = (String) object.get("function");

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Judge judge = (Judge) o;
        return Objects.equals(name, judge.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public List<Judgement> getJudgementList() {
        return judgementsList;
    }

    @Override
    public String toString() {
        return "name: " + name;
    }


}
