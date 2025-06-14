package nz.co.sundar.testautomation.jsonplaceholder.tests;

import io.restassured.response.Response;
import nz.co.sundar.testautomation.jsonplaceholder.base.TestBase;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.CreateUserData;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.CreateUserResponse;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.User;
import nz.co.sundar.testautomation.jsonplaceholder.utils.AssertionsUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.PojoUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.GenericUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
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
/**
 * Test class responsible for verifying the creation of a new user using the JSONPlaceholder API.
 *
 * <p>This test class extends {@link TestBase}, inheriting setup, teardown, and reporting capabilities.
 * It focuses on validating successful user creation by sending a POST request and verifying the
 * response using assertions and structured logging.</p>
 *
 * <p><strong>Features covered:</strong></p>
 * <ul>
 *   <li>Dynamic payload generation for user creation</li>
 *   <li>POJO-based deserialization of API responses</li>
 *   <li>Soft assertions with custom error handling and structured logging</li>
 * </ul>
 *
 * @author Sundarram Krishnakumar
 */
public class CreateUserTests extends TestBase {
    String method = "POST";
    String resourcePath = GenericUtils.userResourcePath;
    // Load and pair rows from create-user CSV
    /**
     * Provides user data for parameterized tests by reading from a CSV file.
     *
     * @return A stream of Arguments containing CreateUserData objects parsed from the CSV file.
     * @throws IOException If there is an error reading the CSV file.
     */
    static Stream<Arguments> userDataProvider() throws IOException {
        Reader createReader = Files.newBufferedReader(Paths.get("src/test/resources/csv/create-user.csv"));

        List<CSVRecord> createRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(createReader).getRecords();

        List<Arguments> arguments = new ArrayList<>();
        for (CSVRecord createRecord : createRecords) {
            CreateUserData createUserData = GenericUtils.parseCreateUserRecord(createRecord);
            arguments.add(Arguments.of(createUserData));
        }
        return arguments.stream();
    }
    /**
     * Test case to verify the successful creation of a user in the JSONPlaceholder API.
     *
     * <p><strong>Test Workflow:</strong></p>
     * <ol>
     *   <li>Logs the HTTP POST operation</li>
     *   <li>Creates a new user with specified details</li>
     *   <li>Sends a POST request to create the user</li>
     *   <li>Validates the response using assertions</li>
     *   <li>Logs the outcome in both the console and test report</li>
     * </ol>
     *
     * <p>This test validates both the API's user creation functionality and the integration with the reporting and assertion framework.</p>
     */
    @ParameterizedTest(name = "CreateUserTests Test #{index} - {0}")
    @MethodSource("userDataProvider")
    public void createBookingTest(CreateUserData createUserData) {
        User user = createUserData.getUser();

        Response response = GenericUtils.createUser(user.getTitle(), user.getBody(), user.getUserId());

        CreateUserResponse userResponse = PojoUtils.convertJsonToPojo(response.asString(), CreateUserResponse.class);

        int userId = userResponse.getUserId();
        int id = userResponse.getId();
        int httpStatusCode = response.getStatusCode();

        logRequestDetails(method,resourcePath);

        AssertionsUtils.assertCreateUserResponse(userResponse, httpStatusCode,
                user.getTitle(), user.getBody(), user.getUserId(), id);

        reportManager.logInfo("Create user response: " + response.asString());
        reportManager.getTest().info("User created with ID: " + id);
        reportManager.logInfo("Response field validation for create user " + userId + " completed successfully.");

    }
}
