package cs.agh.judges.judgementElements;

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

    public HTMLRegulation(String regulationTitle) {
        this.regulationTitle = regulationTitle;
    }
}
