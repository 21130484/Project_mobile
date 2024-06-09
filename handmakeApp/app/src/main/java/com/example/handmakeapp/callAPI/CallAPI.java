package com.example.handmakeapp.callAPI;

import com.example.handmakeapp.model.Cart;
import com.example.handmakeapp.model.CartItemDTO;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Order;
import com.example.handmakeapp.model.Product;
import com.example.handmakeapp.model.ProductDetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CallAPI {

    public final static String SERVER_IP = "192.168.88.252".trim();//ipconfig
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
    Call<List<Order>> getAllOrder(@Query("userId") String userId);

    @GET("api-product?action=getAllProducts")
    Call<List<Product>> getAllProduct();

    @GET("api-product?action=getImageByProductId")
    Call<List<Image>> getImageByIdProduct(@Query("productId") int productId);



    @GET("api-product")
    Call<ProductDetail> getPDById(@Query("action") String action, @Query("productId") int id);

    // INSERT CART
    @FormUrlEncoded
    @POST("cartItem")
    Call<Void> addCartWithItems(
        @Field("userId") String userId,
        @Field("productId") int productId,
        @Field("quantity") int quantity
        );

    //Insert new Rate.
    @FormUrlEncoded
    @POST("addRate")
    Call<Void> addRatings(
            @Field("productId") int productId,
            @Field("userId") String userId,
            @Field("starRatings") int starRatings,
            @Field("comment") String comment,
            @Field("fullName") String fullName
    );

    @GET("cart")
    Call<List<CartItemDTO>> getAllCartItem(@Query("userId") String userId);

    @FormUrlEncoded
    @POST("cart")
    Call<CartItemDTO> updateQuantity(
            @Field("newQuantity") int newQuantity,
            @Field("cartId") int cartId,
            @Field("cartItemId") int cartItemId
    );

    @DELETE("cart")
    Call<Void> deleteItem(@Query("cartId") int cartId,
                          @Query("cartItemId") int cartItemId);

    @FormUrlEncoded
    @POST("checkout")
    Call<Void> checkout(
            @Field("userId") String userId,
            @Field("address") String address,
            @Field("shippingFee") int shippingFee,
            @Field("note") String note,
            @Field("productList") String productList,
            @Field("totalPrice") String totalPrice,
            @Field("consigneeName") String consigneeName,
            @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("addCart")
    Call<Cart> createCart(@Field("userId") String userId);

    @POST("updateOrder")
    Call<Order> updateStatusOrder(
              @Body Order order
    );

}
