package nz.co.sundar.testautomation.jsonplaceholder.tests;

import io.restassured.response.Response;
import nz.co.sundar.testautomation.jsonplaceholder.base.TestBase;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.GetUserData;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.GetUserResponse;
import nz.co.sundar.testautomation.jsonplaceholder.utils.AssertionsUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.GenericUtils;
import nz.co.sundar.testautomation.jsonplaceholder.utils.PojoUtils;
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
 * Test class responsible for verifying the retrieval of user data using the JSONPlaceholder API.
 *
 * <p>This test class extends {@link TestBase}, inheriting setup, teardown, and reporting capabilities.
 * It focuses on validating successful user retrieval by sending a GET request and verifying the
 * response using assertions.</p>
 *
 * <p><strong>Features covered:</strong></p>
 * <ul>
 *   <li>Parameterized tests with data-driven approach using CSV files</li>
 *   <li>POJO-based deserialization of API responses</li>
 *   <li>Assertions for validating user data retrieval</li>
 * </ul>
 *
 * @author Sundarram Krishnakumar
 */
public class GetUserTests extends TestBase {
    String method = "GET";
    String resourcePath = GenericUtils.userResourcePath;
    /**
     * Provides user data for parameterized tests by reading from a CSV file.
     *
     * @return A stream of Arguments containing GetUserData objects
     * @throws IOException If there is an error reading the CSV file
     */
    static Stream<Arguments> userDataProvider() throws IOException {
        Reader getReader = Files.newBufferedReader(Paths.get("src/test/resources/csv/get-user.csv"));

        List<CSVRecord> getRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(getReader).getRecords();

        List<Arguments> arguments = new ArrayList<>();
        for (CSVRecord getRecord : getRecords) {
            GetUserData getUserData = GenericUtils.parseGetUserRecord(getRecord);
            arguments.add(Arguments.of(getUserData));
        }
        return arguments.stream();
    }
    /**
     * Test method to retrieve user data based on the provided GetUserData.
     *
     * @param getUserData The data for the user to be retrieved
     */
    @ParameterizedTest(name = "GetUserTests Test #{index} - {0}")
    @MethodSource("userDataProvider")
    public void getUserTest(GetUserData getUserData) {
        int Id = getUserData.getId();

        Response response = GenericUtils.getUser(Id);
        int httpStatusCode = response.getStatusCode();

        GetUserResponse userGetResponse = PojoUtils.convertJsonToPojo(response.asString(), GetUserResponse.class);

        logRequestDetails(method, resourcePath + "/" + Id);

        AssertionsUtils.assertGetUserResponse(userGetResponse, httpStatusCode, Id);

        reportManager.logInfo("Get user response for id " + Id + ": " + response.asString());
        reportManager.logInfo("Response field validation for get user " + Id + " completed successfully.");
    }
}
