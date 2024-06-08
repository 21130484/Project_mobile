package com.example.handmakeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.handmakeapp.model.CartItemDTO;
import com.example.handmakeapp.model.Order;

import java.util.ArrayList;

public class CustomAdapterOrder extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayList;

    public CustomAdapterOrder(Context context, ArrayList<Order> arrayList) {
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

        Order order = arrayList.get(position);
        TextView idItem = convertView.findViewById(R.id.textViewId);
        TextView nameProduct = convertView.findViewById(R.id.textViewName);
        TextView typeProduct = convertView.findViewById(R.id.textViewType);
        TextView priceProduct = convertView.findViewById(R.id.price);
        TextView xQuantityProduct = convertView.findViewById(R.id.xquantity);
        TextView quantityProduct = convertView.findViewById(R.id.quantity);
        ImageView imgProduct = convertView.findViewById(R.id.imageView);

//        chuyen du lieu vao customeLayout
//        idItem.setText("#"+order.getId());
//        nameProduct.setText(order.getName());
//        typeProduct.setText(order.getDescription());
//        priceProduct.setText(order.getSellingPrice()+"");
//        quantityProduct.setText(order.getQuantity() + "");
//        xQuantityProduct.setText("x"+order.getQuantity());
//        // Sử dụng Glide để tải ảnh
//        Uri imageUri = Uri.parse(order.getPath()+"");
//        Glide.with(convertView).load(imageUri).into(imgProduct);

        return convertView;
    }
}
