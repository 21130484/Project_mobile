package com.example.handmakeapp.home_products;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.cartActivity;
import com.example.handmakeapp.detail_product.DetailActivity;
import com.example.handmakeapp.home_products.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home_products.adapter.SlideAdapter;
import com.example.handmakeapp.home_products.mapping.BannerMapping;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.BannerItem;
import com.example.handmakeapp.model.Product;
import com.example.handmakeapp.model.ProductDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    RecyclerView rv_1;
    RecyclerView rv_2;
    List<Product> topSoldoutProducts;
    List<Product> discountProducts;

    ImageButton btn_toCart;
    ProductListRecyclerViewAdapter adapter;
    ProductListRecyclerViewAdapter adapter_2;

    ViewPager2 viewPager2;
    List<BannerItem> bannerItems = new ArrayList<>();
    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv_1 = findViewById(R.id.rv_1);
        rv_2 = findViewById(R.id.rv_2);
        viewPager2 = findViewById(R.id.viewPager);
        btn_toCart = findViewById(R.id.btn_toCart);

        btn_toCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, cartActivity.class);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_1.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_2.setLayoutManager(layoutManager2);

        new NetworkTask().execute();
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        actionNavigationBottom();
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
    public void actionNavigationBottom() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.account) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.list) {
                    startActivity(new Intent(getApplicationContext(), Products.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), cartActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
    }
    private void getProductById(int id) {
        CallAPI.api.getPDById("getProductDetailsById", id).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                Log.e("Kien", "onSuccess");
                ProductDetail p = response.body();
                if(p != null) {
                    Log.e("Kien", p.toString());

                    //Chuyển sang DetailActivity truyền ProductDetail.
                    Intent intent = new Intent(Home.this, DetailActivity.class);
                    intent.putExtra("productDetail", p);
                    startActivity(intent);

                }

                else {
                    Log.e("Kien", response.code()+ "");
                }
            }
            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                Log.e("Kien", "Failure" + t.getMessage(), t);
            }
        });
    }
}
