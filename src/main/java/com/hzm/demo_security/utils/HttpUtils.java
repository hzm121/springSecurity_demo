package com.hzm.demo_security.utils;

import com.google.gson.*;
import com.sun.deploy.net.HttpResponse;
import org.springframework.http.converter.json.GsonBuilderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtils {
    private static Gson gson = new GsonBuilder().create();
    public static String getBody(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = request.getReader();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    public static String getValueFromJson(String jsonStr,String key){
        JsonElement jsonElement = JsonParser.parseString(jsonStr);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject.get(key).getAsString();
    }

    public static void printJsonStrWithResponse(HttpServletResponse response, Object data) {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(gson.toJson(data));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
