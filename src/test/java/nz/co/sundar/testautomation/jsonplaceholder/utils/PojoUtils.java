package nz.co.sundar.testautomation.jsonplaceholder.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import nz.co.sundar.testautomation.jsonplaceholder.pojo.UserResponse;

import java.io.IOException;

public class PojoUtils {

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
}
