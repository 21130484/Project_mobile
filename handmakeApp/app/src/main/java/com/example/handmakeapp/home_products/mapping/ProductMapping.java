package com.example.handmakeapp.home_products.mapping;

import android.util.Log;

import com.example.handmakeapp.model.Category;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductMapping {
    private static ProductMapping instance;

    public static ProductMapping getInstance() {
        if (instance == null) instance = new ProductMapping();
        return instance;
    }

    public void mappingProductObject(OkHttpClient client, Request request, List<Product> result) {
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
                result.add(new Product(id, name, description, costPrice, sellingPrice, quantity, soldout, categoryId, isSale));
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
    }

    public List<Product> getAllProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getAllProducts").build();
        List<Product> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        return products;
    }

    public List<Product> getTopSoldoutProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getTopSoldoutProducts").build();
        List<Product> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        return products;
    }

    public List<Product> getDiscountProducts() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getDiscountProducts").build();
        List<Product> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        return products;
    }

    public List<Image> getImageByIdProduct(int productId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getImageByProductId&productId=" + productId).build();
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

                    images.add(new Image(id, name, CallAPI.getAbsoluteURL() + path, productId));
                }
            } catch (Exception e) {
                Log.e("get image error", e.toString());
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
        return images;
    }

    public List<Category> getCategories() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getCategories").build();
        List<Category> categories = new ArrayList<>();
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
                    categories.add(new Category(id, name));
                }
            } catch (Exception e) {
                Log.e("get image error", e.toString());
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
        return categories;
    }
}
