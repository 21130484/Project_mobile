package com.example.handmakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Order;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderActivity extends AppCompatActivity {
    TextView fullName, phoneNumber, address, note, totalPrice, feeShip;
    Button btnChange, btnOrder;
    ListView listView;
    CustomAdapterOrder customAdapterOrder;
    ArrayList<Order> arrOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Anhxa();
        Intent intent = getIntent();
//        int orderId = intent.getIntExtra("id");
        CallAPI.api.getAllOrder(1).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body();
                    Log.e("myOrder from API : ", orders.size() + " order");
//                    fullName.setText(orders.get(0).getConsigneeName());
//                    phoneNumber.setText(orders.get(0).getConsigneePhoneNumber());
//                    address.setText(orders.get(0).getAddress());
//                    note.setText(orders.get(0).getNote());
//                    totalPrice.setText(orders.get(0).getTotalPrice()+"");
//                    feeShip.setText(orders.get(0).getShippingFee()+"");
                    Toast.makeText(orderActivity.this, "Call ok", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Response code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(orderActivity.this, "Call error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("API Error", t.getMessage(), t);
                Toast.makeText(orderActivity.this, "Call error",Toast.LENGTH_SHORT).show();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        Toast.makeText(order.this, "oke", Toast.LENGTH_SHORT).show();
    }

    private void Anhxa() {
        fullName = findViewById(R.id.name);
        phoneNumber =  findViewById(R.id.phonenumber);
        address = findViewById(R.id.address);
        note =  findViewById(R.id.note);
        totalPrice = findViewById(R.id.totalPrice);
        feeShip = findViewById(R.id.feeShip);
        btnChange = findViewById(R.id.change);
        btnOrder = findViewById(R.id.doneBtn);
        listView = findViewById(R.id.lv);
        arrOrder = new ArrayList<>();
        customAdapterOrder = new CustomAdapterOrder(orderActivity.this, arrOrder);
        listView.setAdapter(customAdapterOrder);
    }
}