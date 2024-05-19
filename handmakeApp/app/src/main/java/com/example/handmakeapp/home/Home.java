package com.example.handmakeapp.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.R;
import com.example.handmakeapp.home.adapter.ProductListArrayAdapter;
import com.example.handmakeapp.home.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home.mapping.ProductMapping;
import com.example.handmakeapp.model.Product;

import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView rv;
    GridView gv;
    List<Product> products;
    ProductListRecyclerViewAdapter adapter;
    ProductListArrayAdapter gridViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv = findViewById(R.id.rv);
        gv = findViewById(R.id.gv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);

        new NetworkTask().execute();

    }

    private class NetworkTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return ProductMapping.getInstance().getAllProduct();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            Log.e("product list", products.size() + "");
            adapter = new ProductListRecyclerViewAdapter(products);
            rv.setAdapter(adapter);
            gridViewAdapter = new ProductListArrayAdapter(Home.this, R.layout.activity_product_item, products);
            gv.setAdapter(gridViewAdapter);
        }
    }
}
