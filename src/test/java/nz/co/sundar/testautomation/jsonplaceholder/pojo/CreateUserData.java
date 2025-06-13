package nz.co.sundar.testautomation.jsonplaceholder.pojo;

public class CreateUserData {
    public String testcase;
    public String title;
    public String body;
    public int userId;

    public CreateUserData(String testcase, String title, String body, int userId) {
        this.testcase = testcase;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
    public String getTestCase() { return testcase; }

    @Override
    public String toString() {
        return testcase;
    }
}
