package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class LoadPayloads {

    public JSONObject getPayload(String fileName) {

        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            try {
                object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/resources/requestpayloads/" + fileName + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        JSONObject json = (JSONObject) object;
        return json;
    }

    public JSONObject getResponse(String fileName) {

        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try {
            try {
                object = jsonParser.parse(new FileReader(System.getProperty("user.dir") + "/src/main/ResponsePayload/" + fileName + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        JSONObject json = (JSONObject) object;
        return json;
    }
}
