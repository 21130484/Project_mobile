package com.example.handmakeapp.retrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cấu hình Retrofit2 + OkHttpClient + Gson.
 * Đối với method get :
 *  1. REtrofit2 định nghĩa "endpoint" và thực hiện call API.
 *  - endpoint là các đường link (api) dẫn đến file json data.
 *  2. OkHttp yêu cầu HTTP đến API và nhận phản hồi.
 *  3. Gson : Từ file phản hồi (dạng Json) chuyển sang Object Java
 */
public class RetrofitClient {
    private static Retrofit retrofit = null;
    public static Retrofit getClient(String baseUrl) {
        /**
         *
         * Cho phép thời gian ngừng việc đọc/ghi dữ liêụ trong 5s.
         *
         * Chuyển dữ liệu từ JSON -> Java Object = Gson
         *
         */
        OkHttpClient  builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();

        Gson gson = new GsonBuilder().setLenient().create(); // Hỗ trợ convert tốt hơn.


        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }

}
