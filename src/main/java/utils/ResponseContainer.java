package utils;


import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

public class ResponseContainer {

    private static Map<String, ValidatableResponse> dataMap = new HashMap<>();
    private static ResponseContainer responseContainer;

    public static ResponseContainer getInstance() {
        if (responseContainer == null) {
            responseContainer = new ResponseContainer();
            dataMap = new HashMap<>();
        }
        return responseContainer;
    }

    public static void clearCacheInstance() {
        destroyValues();
        responseContainer = null;
    }

    public static void destroyValue(String key) {
        dataMap.remove(key);
    }

    public static void destroyValues() {
        dataMap.clear();
    }


    public Map<String, ValidatableResponse> getDataMap() {
        return dataMap;
    }

    public void setDataMap(String key, ValidatableResponse data) {
        dataMap.put(key, data);
    }

}