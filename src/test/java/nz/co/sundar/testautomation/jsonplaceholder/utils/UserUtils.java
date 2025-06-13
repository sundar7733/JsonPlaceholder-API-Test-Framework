package nz.co.sundar.testautomation.jsonplaceholder.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.User;

public class UserUtils {
 public static String userResourcePath = "/users";

    public static Response createUser(String title, String body, int userId) {

        User user = new User(title, body, userId);

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().body()
                .body(user)
                .when()
                .post(userResourcePath);
    }
}
