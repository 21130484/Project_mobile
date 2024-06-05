package com.example.handmakeapp.home.mapping;

import android.util.Log;
import android.widget.Toast;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Product;
import com.example.handmakeapp.order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductMapping {
    private static ProductMapping instance;

    public static ProductMapping getInstance() {
        if (instance == null) instance = new ProductMapping();
        return instance;
    }

    public List<Product> getAllProduct() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-product?action=getAllProduct").build();
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
                products.add(new Product(id, name, description, costPrice, sellingPrice, quantity, soldout, categoryId, isSale));
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
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
//                    images.add(new Image(id, name, path, productId));
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
