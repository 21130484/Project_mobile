package com.example.handmakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.example.handmakeapp.model.CartItemDTO;
import com.google.android.material.textfield.TextInputEditText;
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
            Double currentTotal = Double.parseDouble(total);
            price.setText(CurrencyFormatter.formatCurrency(currentTotal));
            feeShip.setText("10.000đ");
            totalPrice.setText(CurrencyFormatter.formatCurrency(currentTotal+10));
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
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(orderActivity.this).inflate(R.layout.dialog_layout, null);
                TextInputEditText editName = view1.findViewById(R.id.editName);
                TextInputEditText editPhoneNumber = view1.findViewById(R.id.editPhoneNumber);
                TextInputEditText editAddress = view1.findViewById(R.id.editAddress);
                editName.setText(name.getText());
                editPhoneNumber.setText(phoneNumber.getText());
                editAddress.setText(address.getText());

                AlertDialog.Builder builder = new AlertDialog.Builder(orderActivity.this);
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
                        name.setText(newName);
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