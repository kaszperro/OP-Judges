package cs.agh.judges;


import java.util.Objects;

public class Judge {

    public String name;
    public String function;
    public JudgesSpecialRoles[] specialRoles;


    public Judge(String name, String function, JudgesSpecialRoles[] specialRoles) {
        this.name = name;
        this.function = function;
        this.specialRoles = specialRoles;
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
}
