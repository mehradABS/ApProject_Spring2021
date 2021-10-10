package models;
import java.util.List;

public class Report {

    public static final Object LOCK = new Object();
    private List<Integer> firstId;
    private List<Integer> lastId;

    public Report() {
    }

    public List<Integer> getFirstId() {
        return firstId;
    }

    public void setFirstId(List<Integer> firstId) {
        this.firstId = firstId;
    }

    public List<Integer> getLastId() {
        return lastId;
    }

    public void setLastId(List<Integer> lastId) {
        this.lastId = lastId;
    }
}
