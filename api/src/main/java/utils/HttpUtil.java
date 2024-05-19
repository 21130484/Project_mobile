package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpUtil {

    private String value;

    public HttpUtil(String value) {
        this.value = value;
    }

     public <T> T toModel(Class<T> tClass){
         try {
             return new ObjectMapper().readValue(value, tClass);
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }
     }

    public static HttpUtil of(BufferedReader reader) {
        StringBuilder json = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
        return new HttpUtil(json.toString());
    }
}
