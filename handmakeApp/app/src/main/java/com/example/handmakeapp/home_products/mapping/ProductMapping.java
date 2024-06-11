package com.example.handmakeapp.home_products.mapping;

import android.util.Log;

import com.example.handmakeapp.model.Category;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.example.handmakeapp.model.Rate;

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

    public void mappingProductObject(OkHttpClient client, Request request, List<ProductDetail> result) {
        try {
            Response resp = client.newCall(request).execute();
            String data = resp.body().string();
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                int sellingPrice = jsonObject.getInt("sellingPrice");
                int stock = jsonObject.getInt("stock");
                int categoryId = jsonObject.getInt("categoryId");
                int discountId = jsonObject.getInt("discountId");
                int isSale = jsonObject.getInt("isSale");

                // Mapping imageList
                JSONArray imageArray = jsonObject.getJSONArray("imageList");
                List<Image> imageList = new ArrayList<>();
                for (int j = 0; j < imageArray.length(); j++) {
                    JSONObject imageObject = imageArray.getJSONObject(j);
                    int imageId = imageObject.getInt("id");
                    String imageName = imageObject.getString("name");
                    String imagePath = imageObject.getString("path");
                    imageList.add(new Image(imageId, imageName, CallAPI.getAbsoluteURL() + imagePath, id)); // Assuming Image constructor
                }

                // Mapping rateList
                JSONArray rateArray = jsonObject.getJSONArray("rateList");
                List<Rate> rateList = new ArrayList<>();
                for (int k = 0; k < rateArray.length(); k++) {
                    JSONObject rateObject = rateArray.getJSONObject(k);
                    String fullName = rateObject.getString("fullName");
                    int starRatings = rateObject.getInt("starRatings");
                    String comment = rateObject.getString("comment");
                    long createDate = rateObject.getLong("createDate");
                    rateList.add(new Rate(fullName, starRatings, comment, createDate)); // Assuming Rate constructor
                }
                result.add(new ProductDetail(id, name, description, sellingPrice, stock, categoryId, discountId, isSale, imageList, rateList)); // Assuming ProductDetail constructor
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
    }


    public List<ProductDetail> getAllProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getAllProducts").build();
        List<ProductDetail> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        Log.e("url", CallAPI.getAbsoluteURL() + "/api-product?action=getAllProducts => " + products.size());
        return products;
    }

    public List<ProductDetail> getTopSoldoutProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getTopSoldoutProducts").build();
        List<ProductDetail> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        return products;
    }

    public List<ProductDetail> getDiscountProducts() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getDiscountProducts").build();
        List<ProductDetail> products = new ArrayList<>();
        mappingProductObject(client, request, products);
        return products;
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
