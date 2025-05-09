package utils;

import java.util.HashMap;
import java.util.Map;

import static utils.AuthenticationToken.setAuthenticationToken;
import static utils.Product.*;

public class CacheService {
    private static Map<String, Object> dataMap = new HashMap<>();
    private static CacheService cacheService;

    public static CacheService getInstance() {
        if (cacheService == null) {
            cacheService = new CacheService();
            dataMap = new HashMap<>();
        }
        return cacheService;
    }

    public static void clearCacheInstance() {
        destroyValues();
        cacheService = null;
        setProductId(null);
        setProductName(null);
        setProductType(null);
        setProductPrice(null);
        setAuthenticationToken(null);
    }

    public static void destroyValue(String key) {
        dataMap.remove(key);
    }

    public static void destroyValues() {
        dataMap.clear();
    }


    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(String key, Object data) {
        dataMap.put(key, data);
    }

}