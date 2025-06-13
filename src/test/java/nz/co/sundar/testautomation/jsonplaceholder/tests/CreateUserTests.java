package nz.co.sundar.testautomation.jsonplaceholder.tests;

import io.restassured.response.Response;
import nz.co.sundar.testautomation.jsonplaceholder.base.TestBase;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.CreateUserData;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserResponse;
import nz.co.sundar.testautomation.jsonplaceholder.utils.AssertionsUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.PojoUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.UserUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CreateUserTests extends TestBase {
    String method = "POST";
    // Load and pair rows from both CSVs
    static Stream<Arguments> userDataProvider() throws IOException {
        Reader createReader = Files.newBufferedReader(Paths.get("src/test/resources/csv/create-user.csv"));

        List<CSVRecord> createRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(createReader).getRecords();

        List<Arguments> arguments = new ArrayList<>();
        for (CSVRecord createRecord : createRecords) {
            CreateUserData createUserData = parseCSVRecord(createRecord);
            arguments.add(Arguments.of(createUserData));
        }
        return arguments.stream();
    }
    public static CreateUserData parseCSVRecord(CSVRecord record) {
        return new CreateUserData(
                record.get("testcase"),
                record.get("title"),
                record.get("body"),
                Integer.parseInt(record.get("userId"))

        );
    }
    @ParameterizedTest(name = "CreateUserTests Test #{index} - {0}")
    @MethodSource("userDataProvider")
    public void createBookingTest(CreateUserData createUserData) {

        Response response = UserUtils.createUser(createUserData.title, createUserData.body, createUserData.userId);

        UserResponse userResponse = PojoUtils.convertJsonToUserResponse(response.asString());

        int userId = userResponse.getUserId();
        int id = userResponse.getId();
        int httpStatusCode = response.getStatusCode();

        logRequestDetails(method);

        try{
            Assertions.assertTrue(userId > 0, "UserId should be a positive integer greater than 0. Actual: " + userId);
            reportManager.logPass("UserId is valid: " + userId);
        } catch (NullPointerException e) {
            reportManager.logFail("UserId is null: " + e.getMessage());
            Assertions.fail("UserId is null: " + e.getMessage());
        } catch (AssertionError e) {
            reportManager.logFail("UserId is not valid: " + e.getMessage());
            Assertions.fail("UserId is not valid: " + e.getMessage());
        }

        AssertionsUtils.assertCreateUserResponse(userResponse, httpStatusCode,
                createUserData.title, createUserData.body, createUserData.userId, id);

        reportManager.logInfo("Create user response: " + response.asString());
        reportManager.getTest().info("User created with ID: " + id);
        reportManager.logInfo("Response field validation for create user " + userId + " completed successfully.");



    }
}
