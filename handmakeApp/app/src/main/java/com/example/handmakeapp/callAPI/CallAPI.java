package com.example.handmakeapp.callAPI;

import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Order;
import com.example.handmakeapp.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CallAPI {
    public final static String SERVER_IP = "192.168.5.129";//ipconfig
    public final static String SERVER_PORT = "8080";

    public static String getAbsoluteURL() {
        return "http://" + SERVER_IP + ":" + SERVER_PORT + "/api/";
    }

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();


    CallAPI api = new Retrofit.Builder()
            .baseUrl(getAbsoluteURL())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CallAPI.class);

    @GET("order")
    Call<List<Order>> getAllOrder(@Query("userId") int userId);

    @GET("api-product?action=getAllProduct")
    Call<List<Product>> getAllProduct();

    @GET("api-product?action=getImageByProductId")
    Call<List<Image>> getImageByIdProduct(@Query("productId") int productId);
}
