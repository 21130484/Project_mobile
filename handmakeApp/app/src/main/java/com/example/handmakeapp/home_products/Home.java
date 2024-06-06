package com.example.handmakeapp.home_products;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.handmakeapp.R;
import com.example.handmakeapp.home_products.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home_products.adapter.SlideAdapter;
import com.example.handmakeapp.home_products.mapping.BannerMapping;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.BannerItem;
import com.example.handmakeapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView rv_1;
    RecyclerView rv_2;
    List<Product> topSoldoutProducts;
    List<Product> discountProducts;

    ProductListRecyclerViewAdapter adapter;
    ProductListRecyclerViewAdapter adapter_2;

    ViewPager2 viewPager2;
    List<BannerItem> bannerItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv_1 = findViewById(R.id.rv_1);
        rv_2 = findViewById(R.id.rv_2);
        viewPager2 = findViewById(R.id.viewPager);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_1.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_2.setLayoutManager(layoutManager2);

        new NetworkTask().execute();
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private class NetworkTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            bannerItems = BannerMapping.getInstance().getAll();
            topSoldoutProducts = ProductMapping.getInstance().getTopSoldoutProduct();
            discountProducts = ProductMapping.getInstance().getDiscountProducts();
            return topSoldoutProducts;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            Log.e("banners", "Size of bannerItems: " + bannerItems.size());

            adapter = new ProductListRecyclerViewAdapter(topSoldoutProducts);
            adapter_2 = new ProductListRecyclerViewAdapter(discountProducts);
            rv_1.setAdapter(adapter);
            rv_2.setAdapter(adapter_2);

            SlideAdapter slideAdapter = new SlideAdapter(bannerItems, viewPager2);
            viewPager2.setAdapter(slideAdapter);
        }
    }
}
