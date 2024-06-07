package com.example.handmakeapp.home_products.mapping;

import android.util.Log;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.BannerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BannerMapping {
    private static BannerMapping instance;

    public static BannerMapping getInstance() {
        if (instance == null) instance = new BannerMapping();
        return instance;
    }

    public List<BannerItem> getAll() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(CallAPI.getAbsoluteURL() + "/api-banner").build();
        List<BannerItem> bannerItems = new ArrayList<>();
        try {
            Response resp = client.newCall(request).execute();
            String data = resp.body().string();
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");

                String img_path = jsonObject.getString("img_path");
                bannerItems.add(new BannerItem(title, description, CallAPI.getAbsoluteURL()+img_path));
            }
        } catch (IOException | JSONException e) {
            Log.e("error", e.toString());
        }
        return bannerItems;
    }
}
