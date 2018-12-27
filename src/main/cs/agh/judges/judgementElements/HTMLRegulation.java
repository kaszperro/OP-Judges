package cs.agh.judges.judgementElements;

import java.util.Objects;

public class HTMLRegulation extends AbstractRegulation {
    private String regulationTitle;

    @Override
    public String getTitle() {
        return regulationTitle;
    }

    @Override
    public boolean compareSame(AbstractRegulation other) {
        return getTitle().equals(other.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(regulationTitle);
    }

    public HTMLRegulation(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }
}
