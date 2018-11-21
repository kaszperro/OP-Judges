package cs.agh.judges;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

;import java.util.ArrayList;

public class Judge {

    public String name;
    public String function;
    public ArrayList<JudgesSpecialRoles> specialRoles;

    @JsonCreator
    public void Judge(
            @JsonProperty("name") String name,
            @JsonProperty("function") String function,
            @JsonProperty("specialRoles") ArrayList<JudgesSpecialRoles> specialRoles
    ) {
        this.name = name;
        this.function = function;
        this.specialRoles = specialRoles;
    }


}
