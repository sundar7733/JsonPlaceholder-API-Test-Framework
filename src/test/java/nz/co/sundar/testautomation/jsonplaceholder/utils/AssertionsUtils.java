package nz.co.sundar.testautomation.jsonplaceholder.utils;

import nz.co.sundar.testautomation.jsonplaceholder.pojo.CreateUserResponse;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.GetUserResponse;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

/** AssertionsUtils.java
 * This class provides utility methods for asserting user creation and retrieval responses.
 * It includes methods to validate HTTP status codes, user details, and handle assertion errors.
 */

public class AssertionsUtils {
    private static final ReportManager reportManager = ReportManager.getInstance();
    /**
     * Asserts the response of user creation.
     *
     * @param userResponse The CreateUserResponse object containing the response data.
     * @param httpStatusCode The HTTP status code of the response.
     * @param expectedTitle The expected title in the response.
     * @param expectedBody The expected body in the response.
     * @param expectedUserId The expected user ID in the response.
     * @param expectedId The expected ID in the response.
     */
    public static void assertCreateUserResponse(CreateUserResponse userResponse, int httpStatusCode, String expectedTitle, String expectedBody, int expectedUserId, int expectedId) {

        List<String> errors = new ArrayList<>();

        try {
            assertEquals(201, httpStatusCode, "Validating http status code", errors);
            assertEquals(expectedTitle, userResponse.getTitle(), "Validating Title", errors);
            assertEquals(expectedBody, userResponse.getBody(), "Validating Body", errors);
            assertEquals(expectedUserId, userResponse.getUserId(), "Validating UserId", errors);
            assertTrue(expectedId > 0, "Id should be a positive integer greater than 0. Actual: " + expectedId, errors);

        } catch (Exception e) {
            reportManager.logFail("EXCEPTION: Failed to assert user creation response. Error: " + e.getMessage());
            errors.add("Exception occurred: " + e.getMessage());
        }
        assertAllErrors(errors, reportManager);

    }
    /**
     * Asserts the response of getting a user.
     *
     * @param userGetResponse The GetUserResponse object containing the response data.
     * @param httpStatusCode The HTTP status code of the response.
     * @param expectedId The expected ID in the response.
     */
    public static void assertGetUserResponse(GetUserResponse userGetResponse, int httpStatusCode, int expectedId) {
        List<String> errors = new ArrayList<>();

        try {
            assertEquals(200, httpStatusCode, "Validating http status code", errors);
            assertEquals(expectedId, userGetResponse.getId(), "Validating Id", errors);
            assertNotNullAndLog(userGetResponse.getName(),"Name",errors);
            assertNotNullAndLog(userGetResponse.getUsername(), "Username",errors);
            assertNotNullAndLog(userGetResponse.getEmail(),"Email", errors);
            assertNotNullAndLog(userGetResponse.getAddress(), "Address Object", errors);
            if( userGetResponse.getAddress() != null) {
                assertNotNullAndLog(userGetResponse.getAddress().getStreet(), "Street", errors);
                assertNotNullAndLog(userGetResponse.getAddress().getSuite(), "Suite", errors);
                assertNotNullAndLog(userGetResponse.getAddress().getCity(), "City", errors);
                assertNotNullAndLog(userGetResponse.getAddress().getZipcode(), "Zipcode", errors);
                assertNotNullAndLog(userGetResponse.getAddress().getGeo(), "Geo Object", errors);
                if (userGetResponse.getAddress().getGeo() != null) {
                    assertNotNullAndLog(userGetResponse.getAddress().getGeo().getLat(), "latitude", errors);
                    assertNotNullAndLog(userGetResponse.getAddress().getGeo().getLng(), "longitude", errors);
                }
            }
            assertNotNullAndLog(userGetResponse.getPhone(), "Phone", errors);
            assertNotNullAndLog(userGetResponse.getWebsite(), "Website", errors);
            assertNotNullAndLog(userGetResponse.getCompany(), "Company Object", errors);
            if( userGetResponse.getCompany() != null) {
                assertNotNullAndLog(userGetResponse.getCompany().getName(), "Company Name", errors);
                assertNotNullAndLog(userGetResponse.getCompany().getCatchPhrase(), "Company Catch Phrase", errors);
                assertNotNullAndLog(userGetResponse.getCompany().getBs(), "Company BS", errors);
            }
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
    /**
     * Asserts that an object is not null and logs the result.
     *
     * @param condition The condition to check.
     * @param message  The message to log if the condition is true.
     * @param errors The list to collect errors.
     */
    public static void assertTrue(boolean condition,
                                        String message,
                                        List<String> errors) {
        try {
            Assertions.assertTrue(condition, message);
            reportManager.logPass("PASS: " + message);
        } catch (AssertionError ae) {
            reportManager.logFail("FAIL: " + message);
            errors.add("FAIL: " + message);
        }
    }
}
