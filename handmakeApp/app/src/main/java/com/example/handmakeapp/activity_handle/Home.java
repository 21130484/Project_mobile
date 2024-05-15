package com.example.handmakeapp.activity_handle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;
import com.example.handmakeapp.activity_handle.adapter.ProductListArrayAdapter;
import com.example.handmakeapp.bean.Product;
import com.example.handmakeapp.data_api.ProductAPI;

import java.util.List;

public class Home extends AppCompatActivity {
    GridView gv;
    List<Product> products;
    ProductListArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gv = findViewById(R.id.gv);
        new NetworkTask().execute();

    }

    private class NetworkTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return ProductAPI.getInstance().getAllProduct();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            Log.e("product list", products.size() + "");
            adapter = new ProductListArrayAdapter(Home.this, R.layout.activity_product_item, products);
            gv.setAdapter(adapter);
        }
    }
}
