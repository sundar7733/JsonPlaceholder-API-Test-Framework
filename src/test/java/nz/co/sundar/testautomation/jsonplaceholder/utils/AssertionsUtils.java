package nz.co.sundar.testautomation.jsonplaceholder.utils;

import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserGetResponse;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserResponse;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

/* * AssertionsUtils.java
 * This class provides utility methods for asserting user creation and retrieval responses.
 * It includes methods to validate HTTP status codes, user details, and handle assertion errors.
 */

public class AssertionsUtils {
    private static final ReportManager reportManager = ReportManager.getInstance();
    /**
     * Asserts the response of user creation.
     *
     * @param userResponse The UserResponse object containing the response data.
     * @param httpStatusCode The HTTP status code of the response.
     * @param expectedtitle The expected title in the response.
     * @param expectedbody The expected body in the response.
     * @param expecteduserId The expected user ID in the response.
     * @param expectedid The expected ID in the response.
     */
    public static void assertCreateUserResponse(UserResponse userResponse, int httpStatusCode, String expectedtitle, String expectedbody, int expecteduserId, int expectedid) {

        List<String> errors = new ArrayList<>();

        try {
            assertEquals(201, httpStatusCode, "Validating http status code", errors);
            assertEquals(expectedtitle, userResponse.getTitle(), "Validating Title", errors);
            assertEquals(expectedbody, userResponse.getBody(), "Validating Body", errors);
            assertEquals(expecteduserId, userResponse.getUserId(), "Validating UserId", errors);
            Assertions.assertTrue(expectedid > 0, "Id should be a positive integer greater than 0. Actual: " + expectedid);

        } catch (Exception e) {
            reportManager.logFail("EXCEPTION: Failed to assert user creation response. Error: " + e.getMessage());
            errors.add("Exception occurred: " + e.getMessage());
        }
        assertAllErrors(errors, reportManager);

    }
    /**
     * Asserts the response of getting a user.
     *
     * @param userGetResponse The UserGetResponse object containing the response data.
     * @param httpStatusCode The HTTP status code of the response.
     * @param expectedId The expected ID in the response.
     */
    public static void assertGetUserResponse(UserGetResponse userGetResponse, int httpStatusCode, int expectedId) {
        List<String> errors = new ArrayList<>();

        try {
            assertEquals(200, httpStatusCode, "Validating http status code", errors);
            assertEquals(expectedId, userGetResponse.getId(), "Validating Id", errors);
            assertNotNullAndLog(userGetResponse.getName(),"Name",errors);
            assertNotNullAndLog(userGetResponse.getUsername(), "Username",errors);
            assertNotNullAndLog(userGetResponse.getEmail(),"Email", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getStreet(), "Street", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getSuite(), "Suite", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getCity(), "City", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getZipcode(), "Zipcode", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getGeo().getLat(), "Geo latitude", errors);
            assertNotNullAndLog(userGetResponse.getAddress().getGeo().getLng(), "Geo longitude", errors);
            assertNotNullAndLog(userGetResponse.getPhone(), "Phone", errors);
            assertNotNullAndLog(userGetResponse.getWebsite(), "Website", errors);
            assertNotNullAndLog(userGetResponse.getCompany().getName(), "Company name", errors);
            assertNotNullAndLog(userGetResponse.getCompany().getCatchPhrase(), "Company catch phrase", errors);
            assertNotNullAndLog(userGetResponse.getCompany().getBs(), "Company bs", errors);
        } catch (Exception e) {
            reportManager.logFail("EXCEPTION: Failed to assert get user response. Error: " + e.getMessage());
            errors.add("Exception occurred: " + e.getMessage());
        }
        assertAllErrors(errors, reportManager);
    }
/**
     * Asserts that two objects are equal and logs the result.
     *
     * @param expected The expected value.
     * @param actual The actual value.
     * @param message The message to log.
     * @param errors The list to collect errors.
     * @param <T> The type of the objects being compared.
     */
    public static <T> void assertEquals(T expected, T actual, String message, List<String> errors) {
        try {
            Assertions.assertEquals(expected, actual, message);
            String passMessage = "PASS: " + message + " | Expected: " + expected + ", Actual: " + actual;
            reportManager.logPass(passMessage);
        } catch (AssertionError e) {
            String errorMessage = "FAIL: " + message + " | expected: " + expected + " but was: " + actual;
            reportManager.logFail(errorMessage);
            errors.add(errorMessage);
        }
    }
    /**
     * Asserts that a list of errors is empty and logs each error if present.
     *
     * @param errors The list of errors to check.
     * @param reportManager The ReportManager instance for logging.
     */
    public static void assertAllErrors(List<String> errors, ReportManager reportManager) {
        if (errors != null && !errors.isEmpty()) {
            for (String error : errors) {
                reportManager.logFail("ASSERTION FAILURE: " + error);
            }
            throw new AssertionError("Test failed due to assertion errors: " + errors);
        }
    }
    /**
     * Asserts that an object is not null and logs the result.
     *
     * @param value The object to check.
     * @param fieldName The name of the field being checked.
     * @param errors The list to collect errors.
     */
    public static void assertNotNullAndLog(Object value, String fieldName, List<String> errors) {
        try {
            Assertions.assertNotNull(value, fieldName + " should not be null");
            String passMessage = fieldName + " is not null (" + value + ")";
            reportManager.logPass(passMessage);
        } catch (AssertionError e) {
            String errorMessage = "FAIL: " + fieldName + " is null";
            reportManager.logFail(errorMessage);
            errors.add(errorMessage);
        }
    }
}
