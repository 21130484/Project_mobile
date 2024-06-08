package com.example.handmakeapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.handmakeapp.model.CartItemDTO;
import com.example.handmakeapp.model.Order;

import java.util.ArrayList;

public class CustomAdapterOrder extends BaseAdapter {
    Context context;
    ArrayList<CartItemDTO> arrayList;

    public CustomAdapterOrder(Context context, ArrayList<CartItemDTO> arrayList) {
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
        convertView = inflater.inflate(R.layout.pro_of_order,null);

        CartItemDTO cartItemDTO = arrayList.get(position);
        TextView nameProduct = convertView.findViewById(R.id.textViewName);
        TextView typeProduct = convertView.findViewById(R.id.textViewType);
        TextView priceProduct = convertView.findViewById(R.id.price);
        TextView quantityProduct = convertView.findViewById(R.id.quantity);
        ImageView imgProduct = convertView.findViewById(R.id.imageView);

//        chuyen du lieu vao customeLayout
        nameProduct.setText(cartItemDTO.getName());
        typeProduct.setText(cartItemDTO.getDescription());
        priceProduct.setText(cartItemDTO.getSellingPrice()+"");
        quantityProduct.setText(cartItemDTO.getQuantity() + "");
        // Sử dụng Glide để tải ảnh
        Uri imageUri = Uri.parse(cartItemDTO.getPath()+"");
        Glide.with(convertView).load(imageUri).into(imgProduct);

        return convertView;
    }
}
