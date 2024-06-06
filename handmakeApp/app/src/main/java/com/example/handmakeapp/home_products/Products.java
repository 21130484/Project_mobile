package com.example.handmakeapp.home_products;

import static android.widget.Toast.LENGTH_LONG;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.R;
import com.example.handmakeapp.model.Category;
import com.example.handmakeapp.home_products.adapter.ProductListArrayAdapter;
import com.example.handmakeapp.home_products.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Products extends AppCompatActivity {
    SearchView sv;
    GridView gv;
    Spinner filterProduct;
    List<Category> categories = new ArrayList<>();
    List<String> options = new ArrayList<>();
    HashMap<Integer, Integer> categoryIdMapping = new HashMap<>();//vị trí - categoryId

    List<Product> allProducts;
    ProductListArrayAdapter gridViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        gv = findViewById(R.id.gv);
        sv = findViewById(R.id.searchview);
        filterProduct = findViewById(R.id.filterProductList);
        options.add("Tất cả sản phẩm");
        sv.clearFocus();
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
//        filter
        filterProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectOption = options.get(i);//tên danh mục
                List<Product> filterList = null;
                if (i == 0) {
                    filterList = allProducts;
                    filterCategory(filterList);
                } else if (categoryIdMapping != null && i != 0) {
                    int categotyId = categoryIdMapping.get(i);
//                    >= API 24
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        filterList = allProducts.stream()
                                .filter(product -> product.getCategoryId() == categotyId)
                                .collect(Collectors.toList());
                    }
                    filterCategory(filterList);
                }
                sv.setQuery("", false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Xử lý sự kiện khi không có mục nào được chọn

            }
        });
    }

    private void filterCategory(List<Product> filterList) {
        gridViewAdapter = new ProductListArrayAdapter(Products.this, R.layout.activity_product_item, filterList);
        gv.setAdapter(gridViewAdapter);
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
                Toast.makeText(this, "Không có data", LENGTH_LONG).show();
            } else {
                gridViewAdapter.setFilterList(filterList);
            }
        }
    }

    private class NetworkTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            categories = ProductMapping.getInstance().getCategories();
            allProducts = ProductMapping.getInstance().getAllProduct();
            if (categories != null && !categories.isEmpty()) {
                for (Category c : categories) {
                    options.add(c.getName());
                    categoryIdMapping.put(options.size() - 1, c.getId());
                }
            }
            return allProducts;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            gridViewAdapter = new ProductListArrayAdapter(Products.this, R.layout.activity_product_item, products);
            gv.setAdapter(gridViewAdapter);

            ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(Products.this, android.R.layout.simple_spinner_item, options);
            adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            filterProduct.setAdapter(adapterFilter);
        }
    }
}