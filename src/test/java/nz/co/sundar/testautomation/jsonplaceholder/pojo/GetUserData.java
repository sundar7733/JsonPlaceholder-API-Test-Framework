package nz.co.sundar.testautomation.jsonplaceholder.pojo;

public class GetUserData {
    private final String testcase;
    private final int id;

    public GetUserData(String testcase, int id) {
        this.testcase = testcase;
        this.id       = id;
    }

    public String getTestcase() { return testcase; }
    public int getId()          { return id; }

    @Override
    public String toString() {
        return testcase;
    }
}
