package com.example.handmakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.detail_product.OrderDetailActitvity;
import com.example.handmakeapp.model.Order;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderHistoryActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapterOrderHistory customAdapterOrderHistory;
    ArrayList<Order> arrOrder;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Anhxa();

        CallAPI.api.getAllOrder(user.getUid()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body();
                    Log.e("myOrder from API : ", orders.size() + " order");
                    for (int i = 0; i < orders.size(); i++) {
                        Order order = new Order(orders.get(i).getId(), orders.get(i).getTotalPrice(), orders.get(i).getStatus(), orders.get(i).getConsigneeName(), orders.get(i).getConsigneePhoneNumber(), orders.get(i).getAddress(), orders.get(i).getItemList());
                        arrOrder.add(order);
                    }
                }
            }

                @Override
                public void onFailure(Call<List<Order>> call, Throwable t) {
                    Log.e("API Error", t.getMessage(), t);
                    Toast.makeText(orderHistoryActivity.this, "Call error",Toast.LENGTH_SHORT).show();
                }
        });
        addClickView();
    }
    private void Anhxa() {
        listView = findViewById(R.id.lv);
        arrOrder = new ArrayList<>();
        customAdapterOrderHistory = new CustomAdapterOrderHistory(orderHistoryActivity.this, arrOrder);
        listView.setAdapter(customAdapterOrderHistory);
    }

    private void addClickView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getItemAtPosition(position);
                Intent intent = new Intent(view.getContext(), OrderDetailActitvity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
    }
}