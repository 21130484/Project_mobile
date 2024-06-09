package com.example.handmakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handmakeapp.account.Account;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.home_products.Home;
import com.example.handmakeapp.home_products.Products;

import com.example.handmakeapp.model.CartItemDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cartActivity extends AppCompatActivity {

    Button next;
    ImageButton removeItem;
    TextView totalPrice;
    ListView lv;
    CustomAdapterCart customAdapterCart;
    ArrayList<CartItemDTO> arrCartItems;
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        actionNavigationBottom();

        CallAPI.api.getAllCartItem(4).enqueue(new Callback<List<CartItemDTO>>() {
            @Override
            public void onResponse(Call<List<CartItemDTO>> call, Response<List<CartItemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CartItemDTO> cartItems = response.body();
                    Log.e("myCart from API : ", cartItems.size() + " cartItem");
                    double total = 1;
                    for (int i = 0 ; i < cartItems.size(); i++){
                        total += cartItems.get(i).getSellingPrice();
                        CartItemDTO item = new CartItemDTO(cartItems.get(i).getId(),cartItems.get(i).getCartId(),cartItems.get(i).getName(), cartItems.get(i).getDescription(),cartItems.get(i).getSellingPrice(),CallAPI.getAbsoluteURL()+cartItems.get(i).getPath(),cartItems.get(i).getQuantity());
                        arrCartItems.add(item);
                    }
                    next.setText("Mua hàng ("+cartItems.size()+")");
                    updateTotalPrice();
//                    totalPrice.setText(total+"");
                    customAdapterCart.notifyDataSetChanged();
                    Toast.makeText(cartActivity.this, "Call ok", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("API Error", "Response code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(cartActivity.this, "Call error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CartItemDTO>> call, Throwable t) {
                Log.e("API Error", t.getMessage(), t);
                Toast.makeText(cartActivity.this, "Call error",Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(cartActivity.this,orderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cartItems", arrCartItems);
                intent1.putExtras(bundle);
                intent1.putExtra("totalPrice", totalPrice.getText().toString());
                startActivity(intent1);
            }
        });
//        Toast.makeText(order.this, "oke", Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CartItemDTO item : arrCartItems) {
            total += item.getSellingPrice() * item.getQuantity();
        }
        totalPrice.setText(CurrencyFormatter.formatCurrency(total));
    }

    private void Anhxa() {
        next = findViewById(R.id.next);
        removeItem = findViewById(R.id.removeItem);
        totalPrice = findViewById(R.id.totalPrice);
        lv = findViewById(R.id.lv);
        arrCartItems = new ArrayList<>();
        customAdapterCart = new CustomAdapterCart(cartActivity.this, arrCartItems,totalPrice);
        lv.setAdapter(customAdapterCart);
    }
    public void actionNavigationBottom() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.account) {
                    return true;
                } else if (id == R.id.home) {
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.list) {
                    startActivity(new Intent(getApplicationContext(), Products.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (id == R.id.cart) {
                    startActivity(new Intent(getApplicationContext(), cartActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });
    }
}