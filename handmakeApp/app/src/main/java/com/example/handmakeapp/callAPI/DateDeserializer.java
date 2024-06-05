package com.example.handmakeapp.callAPI;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new Date(json.getAsJsonPrimitive().getAsLong());
        //           if(json.isJsonPrimitive() && json.getAsJsonPrimitive().isNumber()){
//               long timestamp = json.getAsJsonPrimitive().getAsLong();
//               return new Date(timestamp);
//           }else {
//
//               throw  new JsonParseException("Error Timestamp!");
//           }

    }
}
