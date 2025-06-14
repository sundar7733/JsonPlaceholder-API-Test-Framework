package nz.co.sundar.testautomation.jsonplaceholder.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserGetResponse;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserResponse;

import java.io.IOException;
/** Utility class to convert JSON strings to POJOs for UserResponse and UserGetResponse
 */
public class PojoUtils {
// Utility class to convert JSON strings to POJOs for UserResponse
    public static UserResponse convertJsonToUserResponse(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserResponse userResponse = null;
        try {
            userResponse = objectMapper.readValue(jsonString, UserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userResponse;
    }
 // Utility class to convert JSON strings to POJOs for UserGetResponse
    public static UserGetResponse convertJsonToGetUserResponse(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserGetResponse userGetResponse = null;
        try {
            userGetResponse = objectMapper.readValue(jsonString, UserGetResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userGetResponse;
    }
}