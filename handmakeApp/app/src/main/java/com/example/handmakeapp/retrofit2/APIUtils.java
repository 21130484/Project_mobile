package com.example.handmakeapp.retrofit2;

/**
 * Cung cấp đường dẫn đến Server.
 *
 *
 */
public class APIUtils {
    public static final String baseUrl = "http://10.0.243.219:2204/ToolApi/api/v3/";
    // thay đổi cho mỗi người.
    /**
     *  DataClient viết method gì đó
     *  => APIUtils là môi trường trung gian để gửi đến Server
     *  => Chứa vào DataClient.
     */
    public static DataClient getData() {
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }

}
