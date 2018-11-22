package cs.agh.judges;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Judge implements IJudgmentTracker {

    public String name;
    public String function;
    public JudgesSpecialRole[] specialRoles;


    public Judge(String name, String function, JudgesSpecialRole[] specialRoles) {
        this.name = name;
        this.function = function;
        this.specialRoles = specialRoles;
    }

    public Judge(JSONObject object) {
        name = (String) object.get("name");
        function = (String) object.get("function");
        List<JudgesSpecialRole> specialRolesList = new LinkedList<>();
        JSONArray jsonArrayRoles = (JSONArray) object.get("specialRoles");
        for (Object objectRole : jsonArrayRoles) {
            String roleString = (String) objectRole;
            specialRolesList.add(JudgesSpecialRole.valueOf(roleString));
        }
        specialRoles = new JudgesSpecialRole[specialRolesList.size()];
        specialRoles = specialRolesList.toArray(specialRoles);
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
    public String toString() {
        return "name: " + name + ", function: " + function;
    }
}
