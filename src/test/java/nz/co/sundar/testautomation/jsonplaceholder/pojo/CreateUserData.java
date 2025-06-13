package nz.co.sundar.testautomation.jsonplaceholder.pojo;

public class CreateUserData {
    private final String testcase;
    private final User user;

    public CreateUserData(String testcase, User user) {
        this.testcase = testcase;
        this.user = user;
    }
    public String getTestCase() { return testcase; }
    public User getUser()       { return user; }

    @Override
    public String toString() {
        return testcase;
    }
}
