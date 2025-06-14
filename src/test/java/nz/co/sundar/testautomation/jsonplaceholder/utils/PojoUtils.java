package nz.co.sundar.testautomation.jsonplaceholder.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
/**
 * Utility class for converting JSON strings to POJOs using Jackson.
 */
public final class PojoUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private PojoUtils() {
    // Private constructor to prevent instantiation
    }

    public static <T> T convertJsonToPojo(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert JSON to " + clazz.getSimpleName(), e);
        }
    }
}
