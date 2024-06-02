package com.example.handmakeapp.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.R;
import com.example.handmakeapp.home.adapter.ProductListArrayAdapter;
import com.example.handmakeapp.home.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home.mapping.ProductMapping;
import com.example.handmakeapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    SearchView sv;
    RecyclerView rv;
    GridView gv;

    List<Product> allProducts;
    List<Product> topSoldoutProducts;
    ProductListRecyclerViewAdapter adapter;
    ProductListArrayAdapter gridViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv = findViewById(R.id.rv);
        gv = findViewById(R.id.gv);
        sv = findViewById(R.id.searchview);
        sv.clearFocus();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);

        // Execute network task to fetch data
        new NetworkTask().execute();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterText(newText);
                return true;
            }
        });
    }
    private void filterText(String newText) {
        List<Product> filterList = new ArrayList<>();
        if (allProducts != null) {
            for (Product p : allProducts) {
                if (p.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filterList.add(p);
                }
            }
            if (filterList.isEmpty()) {
                gridViewAdapter.setFilterList(filterList);
                Toast.makeText(this, "Không có data", Toast.LENGTH_LONG).show();
            } else {
                gridViewAdapter.setFilterList(filterList);
            }
        }
    }
    private class NetworkTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            topSoldoutProducts = ProductMapping.getInstance().getTopSoldoutProduct();
            allProducts =  ProductMapping.getInstance().getAllProduct();
            return allProducts;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            Log.e("product list", products.size() + "");
            adapter = new ProductListRecyclerViewAdapter(topSoldoutProducts);
            rv.setAdapter(adapter);
            gridViewAdapter = new ProductListArrayAdapter(Home.this, R.layout.activity_product_item, products);
            gv.setAdapter(gridViewAdapter);
        }
    }
}
