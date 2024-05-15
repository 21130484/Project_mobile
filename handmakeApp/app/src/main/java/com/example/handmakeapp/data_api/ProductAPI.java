package com.example.handmakeapp.data_api;

import android.util.Log;

import com.example.handmakeapp.bean.Image;
import com.example.handmakeapp.bean.Product;
import com.example.handmakeapp.ServerInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductAPI {
    private static ProductAPI instance;

    public static ProductAPI getInstance() {
        if (instance == null) instance = new ProductAPI();
        return instance;
    }

    public List<Product> getAllProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(ServerInformation.getAbsoluteURL() + "/api-product?action=getAllProduct").build();
        List<Product> products = new ArrayList<>();
        try {
            Response resp = client.newCall(request).execute();
            String data = resp.body().string();
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                double costPrice = jsonObject.getDouble("costPrice");
                double sellingPrice = jsonObject.getDouble("sellingPrice");
                int quantity = jsonObject.getInt("quantity");
                int soldout = jsonObject.getInt("soldout");
                int categoryId = jsonObject.getInt("categoryId");
                int discountId = jsonObject.getInt("discountId");
                int isSale = jsonObject.getInt("isSale");
                products.add(new Product(name, description, costPrice, sellingPrice, quantity, soldout, categoryId, isSale));
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
        return products;
    }

    public List<Image> getImageByIdProduct(int productId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(ServerInformation.getAbsoluteURL() + "/api-product?action=getImageByIdProduct&productId=" + productId).build();
        List<Image> images = new ArrayList<>();
        try {
            Response resp = client.newCall(request).execute();
            String data = resp.body().string();
            JSONArray jsonArray = new JSONArray(data);
            try {
                resp = client.newCall(request).execute();
                data = resp.body().string();
                jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String path = jsonObject.getString("path");
                    images.add(new Image(id, name, path, productId));
                }
            } catch (Exception e) {
                Log.e("get image error", e.toString());
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
        return images;
    }
}
