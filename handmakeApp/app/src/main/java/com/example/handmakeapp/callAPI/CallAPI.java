package com.example.handmakeapp.callAPI;

import com.example.handmakeapp.model.CartItemDTO;
import com.example.handmakeapp.model.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CallAPI {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateDeserializer())
            .create();


    CallAPI api = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api_war_exploded/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CallAPI.class);

    @GET("order")
    Call<List<Order>> getAllOrder(@Query("userId") int userId);
    @GET("cart")
    Call<List<CartItemDTO>> getAllCartItem(@Query("userId") int userId);
    @FormUrlEncoded
    @POST("cart")
    Call<CartItemDTO> updateQuantity(
            @Field("newQuantity") int newQuantity,
            @Field("cartId") int cartId,
            @Field("cartItemId") int cartItemId
    );
}
