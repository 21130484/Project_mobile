package com.example.handmakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.handmakeapp.account.Account;
import com.example.handmakeapp.home_products.Home;
import com.example.handmakeapp.listProduct.productList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class cart extends AppCompatActivity {
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        actionNavigationBottom();
    }

    public void actionNavigationBottom() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.cart);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.cart) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.list) {
                    startActivity(new Intent(getApplicationContext(), productList.class));
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