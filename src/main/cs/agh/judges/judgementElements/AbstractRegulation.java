package cs.agh.judges.judgementElements;

public abstract class AbstractRegulation {
    public abstract String getTitle();

    public abstract boolean compareSame(AbstractRegulation other);

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractRegulation))
            return false;

        if (getClass() != o.getClass()) {
            return getTitle().equals(((AbstractRegulation) o).getTitle());
        }
        return compareSame((AbstractRegulation) o);
    }

}
