package com.example.handmakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handmakeapp.account.Account;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.home_products.Home;
import com.example.handmakeapp.home_products.Products;

import com.example.handmakeapp.model.CartItemDTO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    Button next;
    ImageButton removeItem;
    TextView totalPrice, textNotify;
    ListView lv;
    CustomAdapterCart customAdapterCart;
    ArrayList<CartItemDTO> arrCartItems;
    BottomNavigationView bottomNavigation;
    LinearLayout thanhToan;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        actionNavigationBottom();

        if (user != null) {

            Log.e("QUIII", user.getDisplayName());

            CallAPI.api.getAllCartItem(user.getUid()).enqueue(new Callback<List<CartItemDTO>>() {
                @Override
                public void onResponse(Call<List<CartItemDTO>> call, Response<List<CartItemDTO>> response) {
                        List<CartItemDTO> cartItems = response.body();
                        if (cartItems.size() !=0) {
                            double total = 1;
                            for (int i = 0 ; i < cartItems.size(); i++){
                                total += cartItems.get(i).getSellingPrice();
                                CartItemDTO item = new CartItemDTO(cartItems.get(i).getId(),cartItems.get(i).getCartId(),cartItems.get(i).getName(), cartItems.get(i).getDescription(),cartItems.get(i).getSellingPrice(),CallAPI.getAbsoluteURL()+cartItems.get(i).getPath(),cartItems.get(i).getQuantity());
                                arrCartItems.add(item);
                            }
                            next.setText("Mua hàng ("+cartItems.size()+")");
                            updateTotalPrice();
                            customAdapterCart.notifyDataSetChanged();
                        } else {
                            thanhToan.setVisibility(View.GONE);
                            textNotify.setVisibility(View.VISIBLE);
                            textNotify.setText("Giỏ hàng đang trống");
                        }
                }

                @Override
                public void onFailure(Call<List<CartItemDTO>> call, Throwable t) {
                    Log.e("API Error", t.getMessage(), t);
                    Toast.makeText(CartActivity.this, "Call error",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            thanhToan.setVisibility(View.GONE);
            textNotify.setVisibility(View.VISIBLE);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CartActivity.this, OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cartItems", arrCartItems);
                intent1.putExtras(bundle);

                int price = convertVNDtoInt(totalPrice.getText().toString());
                intent1.putExtra("totalPrice", price+"");
                startActivity(intent1);
            }
        });
//        Toast.makeText(order.this, "oke", Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice() {
        int total = 0;
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
        customAdapterCart = new CustomAdapterCart(CartActivity.this, arrCartItems,totalPrice);
        lv.setAdapter(customAdapterCart);
        thanhToan = findViewById(R.id.thanhToan);
        textNotify = findViewById(R.id.textNotify);
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
                    startActivity(new Intent(getApplicationContext(), Products.class));
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

    public static int convertVNDtoInt(String priceVND) {
        String numerics = priceVND.replaceAll("[^\\d]", "");
        return Integer.parseInt(numerics);
    }
}