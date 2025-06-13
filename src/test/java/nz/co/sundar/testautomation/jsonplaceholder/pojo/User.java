package nz.co.sundar.testautomation.jsonplaceholder.pojo;

public class User {
 private String title;
 private String body;
 private int userId;
 public User () {
 }
    public User(String title, String body, int userId) {
    this.title = title;
    this.body = body;
    this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String toString() {
        return "User{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }
}
