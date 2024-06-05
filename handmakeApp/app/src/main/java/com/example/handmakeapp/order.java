package com.example.handmakeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Order;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class order extends AppCompatActivity {
    TextView fullName, phoneNumber, address, note, totalPrice, feeShip;
    Button btnChange, btnOrder;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Anhxa();
        CallAPI.api.getAllOrder(1).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> orders = response.body();
                    Log.e("myOrder from API : ", orders.size() + " order");
                    fullName.setText(orders.get(0).getConsigneeName());
                    phoneNumber.setText(orders.get(0).getConsigneePhoneNumber());
                    address.setText(orders.get(0).getAddress());
                    note.setText(orders.get(0).getNote());
                    totalPrice.setText(orders.get(0).getTotalPrice()+"");
                    feeShip.setText(orders.get(0).getShippingFee()+"");
                    Toast.makeText(order.this, "Call ok", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Response code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(order.this, "Call error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("API Error", t.getMessage(), t);
                Toast.makeText(order.this, "Call error",Toast.LENGTH_SHORT).show();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(order.this).inflate(R.layout.dialog_layout, null);
                TextInputEditText editName = view1.findViewById(R.id.editName);
                TextInputEditText editPhoneNumber = view1.findViewById(R.id.editPhoneNumber);
                TextInputEditText editAddress = view1.findViewById(R.id.editAddress);
                editName.setText(fullName.getText());
                editPhoneNumber.setText(phoneNumber.getText());
                editAddress.setText(address.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(order.this);
                builder.setTitle("Thay đổi thông tin")
                        .setView(view1)
                        .setPositiveButton("Hoàn tất", null)
                        .setNegativeButton("Hủy", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                // Set màu và kiểu chữ cho nút "Hoàn tất"
                positiveButton.setTextColor(Color.GREEN);
                positiveButton.setTextSize(18);


                // Set màu và kiểu chữ cho nút "Hủy"
                negativeButton.setTextColor(Color.GRAY);
                negativeButton.setTextSize(18);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = editName.getText().toString();
                        String newPhoneNumber = editPhoneNumber.getText().toString();
                        String newAddress = editAddress.getText().toString();
                        fullName.setText(newName);
                        phoneNumber.setText(newPhoneNumber);
                        address.setText(newAddress);

                        alertDialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
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
    }
}