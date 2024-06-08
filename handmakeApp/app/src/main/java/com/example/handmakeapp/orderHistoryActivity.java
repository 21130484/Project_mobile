package com.example.handmakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.CartItemDTO;
import com.example.handmakeapp.model.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderHistoryActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapterOrderHistory customAdapterOrderHistory;
    ArrayList<Order> arrOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Anhxa();
//        Intent intent = getIntent();
//        int orderId = intent.getIntExtra("id");
        CallAPI.api.getAllOrder(4).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body();
                    Log.e("myOrder from API : ", orders.size() + " order");
                    for (int i = 0 ; i < orders.size(); i++){
                        Order order = new Order(orders.get(i).getId(), orders.get(i).getTotalPrice(),orders.get(i).getStatus(),orders.get(i).getItemList());
                        arrOrder.add(order);
                    }
                    customAdapterOrderHistory.notifyDataSetChanged();
                    Toast.makeText(orderHistoryActivity.this, "Call ok", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Response code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(orderHistoryActivity.this, "Call error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("API Error", t.getMessage(), t);
                Toast.makeText(orderHistoryActivity.this, "Call error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Anhxa() {
        listView = findViewById(R.id.lv);
        arrOrder = new ArrayList<>();
        customAdapterOrderHistory = new CustomAdapterOrderHistory(orderHistoryActivity.this, arrOrder);
        listView.setAdapter(customAdapterOrderHistory);
    }
}