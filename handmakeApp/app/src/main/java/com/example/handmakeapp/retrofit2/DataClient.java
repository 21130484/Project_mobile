package com.example.handmakeapp.retrofit2;

import com.example.handmakeapp.model.ProductDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Path;

public interface DataClient {
    @GET("products/{id}")
    Call<ProductDetail> getProductById(@Path("id") int id);

}
