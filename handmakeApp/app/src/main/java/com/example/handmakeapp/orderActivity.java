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

import com.example.handmakeapp.model.CartItemDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderActivity extends AppCompatActivity {
    TextView price, totalPrice, address, note, feeShip, phoneNumber, name;
    ArrayList<CartItemDTO> cartItems;
    List<String> arrProductId = new ArrayList<>();
    Button back, btnChange, btnOrder;
    ListView listView;
    CustomAdapterOrder customAdapterOrder;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Anhxa();
        Bundle bundle = getIntent().getExtras();
        List<CartItemDTO> data = new ArrayList<>();
        if (bundle != null) {
            String total = getIntent().getStringExtra("totalPrice");
            price.setText(total + "đ");
            feeShip.setText("10.000đ");
//            totalPrice.setText(total + "đ");
            data = (ArrayList<CartItemDTO>) bundle.getSerializable("cartItems");
        }
        for (int i = 0; i < data.size(); i++){
            cartItems.add(data.get(i));
            arrProductId.add(String.valueOf(data.get(i).getId())); // id of cartItem
        }
        Log.e("cartItems : ", cartItems.size() + "");
        customAdapterOrder.notifyDataSetChanged();
        Toast.makeText(orderActivity.this, "Ok", Toast.LENGTH_SHORT).show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    CallAPI.api.checkout(user.getUid(), address.getText().toString(),
                            10000, note.getText().toString(), arrProductId.toString(),
                            totalPrice.getText().toString(),
                            name.getText().toString(), phoneNumber.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            customAdapterOrder.notifyDataSetChanged();
                            Intent intent = new Intent(orderActivity.this, orderHistoryActivity.class);
                            startActivity(intent);
                            Toast.makeText(orderActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(orderActivity.this, "Add error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
//        btnOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        Toast.makeText(order.this, "oke", Toast.LENGTH_SHORT).show();
    }

    private void Anhxa() {
        price = findViewById(R.id.price);
        totalPrice = findViewById(R.id.totalPrice);
        feeShip = findViewById(R.id.feeShip);
        address = findViewById(R.id.address);
        note = findViewById(R.id.note);
        back = findViewById(R.id.back);
        btnChange = findViewById(R.id.change);
        btnOrder = findViewById(R.id.doneBtn);
        listView = findViewById(R.id.lv);
        cartItems = new ArrayList<>();
        customAdapterOrder = new CustomAdapterOrder(orderActivity.this, cartItems);
        listView.setAdapter(customAdapterOrder);
        phoneNumber = findViewById(R.id.phoneNumber);
        name = findViewById(R.id.name);
    }
}