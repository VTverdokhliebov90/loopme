package test.interview.loopme.campaign.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.List;

public final class TestUtils {

    public static <T> T jsonToObj(String json, Class<T> targetClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, targetClass);
    }

    public static <T> List<T> jsonToObj(String json, TypeReference<List<T>> targetClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, targetClass);
    }

    public static String objToJson(Object o) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer();
        return ow.writeValueAsString(o);
    }
}
