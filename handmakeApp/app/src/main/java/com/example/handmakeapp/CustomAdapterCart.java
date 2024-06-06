package com.example.handmakeapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.CartItemDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapterCart extends BaseAdapter {
    Context context;
    ArrayList<CartItemDTO> arrayList;

    public CustomAdapterCart(Context context, ArrayList<CartItemDTO> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.pro_of_cart, null);

        CartItemDTO cartItems = arrayList.get(position);
        TextView idItem = convertView.findViewById(R.id.textViewId);
        TextView nameProduct = convertView.findViewById(R.id.textViewName);
        TextView typeProduct = convertView.findViewById(R.id.textViewType);
        TextView priceProduct = convertView.findViewById(R.id.price);
        TextView xQuantityProduct = convertView.findViewById(R.id.xquantity);
        TextView quantityProduct = convertView.findViewById(R.id.quantity);
        ImageView imgProduct = convertView.findViewById(R.id.imageView);
        Button increase = convertView.findViewById(R.id.increase);
        Button decrease = convertView.findViewById(R.id.decrease);

//        chuyen du lieu vao customeLayout
        idItem.setText("#"+cartItems.getId());
        nameProduct.setText(cartItems.getName());
        typeProduct.setText(cartItems.getDescription());
        priceProduct.setText(cartItems.getSellingPrice()+"");
        quantityProduct.setText(cartItems.getQuantity() + "");
        xQuantityProduct.setText("x"+cartItems.getQuantity());
        // Sử dụng Glide để tải ảnh
        Uri imageUri = Uri.parse(cartItems.getPath()+"");
        Glide.with(convertView).load(imageUri).into(imgProduct);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItems.getQuantity();
                currentQuantity++;
                cartItems.setQuantity(currentQuantity);
                quantityProduct.setText(String.valueOf(currentQuantity));
                xQuantityProduct.setText("x" + currentQuantity);
                notifyDataSetChanged(); // Cập nhật lại ListView
                CallAPI.api.updateQuantity(currentQuantity, cartItems.getCartId(), cartItems.getId()).enqueue(new Callback<CartItemDTO>() {
                    @Override
                    public void onResponse(Call<CartItemDTO> call, Response<CartItemDTO> response) {
                        boolean isUpdated = response.isSuccessful();
                        if(isUpdated){
                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CartItemDTO> call, Throwable t) {
                        Toast.makeText(context, "Update error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItems.getQuantity();
                if (currentQuantity > 0) {
                    currentQuantity--;
                    cartItems.setQuantity(currentQuantity);
                    quantityProduct.setText(String.valueOf(currentQuantity));
                    xQuantityProduct.setText("x" + currentQuantity);
                    notifyDataSetChanged(); // Cập nhật lại ListView
                    CallAPI.api.updateQuantity(currentQuantity, cartItems.getCartId(), cartItems.getId()).enqueue(new Callback<CartItemDTO>() {
                        @Override
                        public void onResponse(Call<CartItemDTO> call, Response<CartItemDTO> response) {
                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<CartItemDTO> call, Throwable t) {
                            Toast.makeText(context, "Update error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return convertView;
    }
}
