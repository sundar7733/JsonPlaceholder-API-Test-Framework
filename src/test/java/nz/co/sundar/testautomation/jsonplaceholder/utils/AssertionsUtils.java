package nz.co.sundar.testautomation.jsonplaceholder.utils;

import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserResponse;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class AssertionsUtils {
    private static final ReportManager reportManager = ReportManager.getInstance();

    public static void assertCreateUserResponse(UserResponse userResponse, int httpStatusCode, String expectedtitle, String expectedbody, int expecteduserId, int expectedid) {


        List<String> errors = new ArrayList<>();

        try {
            assertEquals(200, httpStatusCode, "Validating http status code", errors);
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
    public static void assertAllErrors(List<String> errors, ReportManager reportManager) {
        if (errors != null && !errors.isEmpty()) {
            for (String error : errors) {
                reportManager.logInfo("ASSERTION FAILURE: " + error);
            }
            throw new AssertionError("Test failed due to assertion errors: " + errors);
        }
    }
}
