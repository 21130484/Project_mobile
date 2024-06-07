package com.example.handmakeapp.listProduct;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.account.Account;
import com.example.handmakeapp.R;
import com.example.handmakeapp.cart;
import com.example.handmakeapp.home_products.Home;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class productList extends AppCompatActivity {
    ListView listProduct1;
    ListView listProduct2;
    Spinner filterProduct;

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productlist);
        AnhXa();
        actionNavigationBottom();
    }

    private void AnhXa() {
        listProduct1 = (ListView) findViewById(R.id.listProduct);
        String[] arr = {"Mũ","Tất","Kính","Quần","Áo"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr);
        listProduct1.setAdapter(adapter);
        listProduct2 = (ListView) findViewById(R.id.listProducts);
        String[] arr2 = {"dép","giày","mũ","ống tay","bó gối"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arr2);
        listProduct2.setAdapter(adapter2);

        String[] option = {"Khung Ảnh HandMade","Thiệp HandMade","Đồ Dùng HandMade"};
        ArrayAdapter<String> adapterFilter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,option);
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterProduct = findViewById(R.id.filterProductList);
        filterProduct.setAdapter(adapterFilter);
//        filterProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectOption = option[position];
//                System.out.println(selectOption);
//            }
//        });

    }
    public void actionNavigationBottom() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.list);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.list) {
                    return true;
                } else if (id == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), cart.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.account) {
                    startActivity(new Intent(getApplicationContext(), Account.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
    }
}
