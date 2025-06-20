package nz.co.sundar.testautomation.jsonplaceholder.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.CreateUserData;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.GetUserData;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.User;
import org.apache.commons.csv.CSVRecord;

/** * Utility class for handling user-related operations in the JSONPlaceholder API.
 * Provides methods to create users, parse CSV records, and retrieve user data.
 */
public class UserUtils {
 public static String userResourcePath = "/users";
 /**
     * Creates a user with the specified title, body, and userId.
     *
     * @param title  The title of the user
     * @param body   The body of the user
     * @param userId The ID of the user
     * @return A Response object containing the API response
     */
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
    /**
     * Parses a CSV record into a CreateUserData object.
     *
     * @param record The CSVRecord to parse
     * @return A CreateUserData object containing the parsed values
     */
    public static CreateUserData parseCreateUserRecord(CSVRecord record) {
        try {
            int userId = Integer.parseInt(record.get("userId"));
            User user = new User(
                    record.get("title"),
                    record.get("body"),
                    userId
            );
            return new CreateUserData(record.get("testcase"), user);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid userId in record: " + record.get("userId"), e);
        }
    }
    /**
     * Parses a CSV record into a GetUserData object.
     *
     * @param record The CSVRecord to parse
     * @return A GetUserData object containing the parsed values
     */
    public static GetUserData parseGetUserRecord(CSVRecord record) {
        try {
            int id = Integer.parseInt(record.get("Id"));
            return new GetUserData(record.get("testcase"), id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id in record: " + record.get("Id"), e);
        }
    }
    /**
     * Retrieves a user by ID from the JSONPlaceholder API.
     *
     * @param id The ID of the user to retrieve
     * @return A Response object containing the API response
     */
    public static Response getUser(int id) {
        String userResourcePathWithId = userResourcePath + "/" + id;

        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get(userResourcePathWithId);
    }
}
