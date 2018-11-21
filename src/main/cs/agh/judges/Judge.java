package cs.agh.judges;


public class Judge {

    public String name;
    public String function;
    public JudgesSpecialRoles[] specialRoles;


    public void Judge(String name, String function, JudgesSpecialRoles[] specialRoles) {
        this.name = name;
        this.function = function;
        this.specialRoles = specialRoles;
    }


}
