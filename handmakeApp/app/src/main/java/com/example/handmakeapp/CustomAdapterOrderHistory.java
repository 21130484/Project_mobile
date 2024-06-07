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
import com.example.handmakeapp.model.Order;
import com.example.handmakeapp.model.OrderItem;

import java.util.ArrayList;

public class CustomAdapterOrderHistory extends BaseAdapter {
    Context context;
    ArrayList<Order> arrayList;

    public CustomAdapterOrderHistory(Context context, ArrayList<Order> arrayList) {
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
        convertView = inflater.inflate(R.layout.pro_of_order_his,null);

        Order order = arrayList.get(position);
        OrderItem orderItem = order.getItemList().get(0); // san pham dai dien cho don hang (first)
        TextView orderId = convertView.findViewById(R.id.textViewId);
        TextView orderStatus = convertView.findViewById(R.id.textViewStatus);
        ImageView imgProduct = convertView.findViewById(R.id.imageView);
        TextView nameProduct = convertView.findViewById(R.id.textViewName);
        TextView typeProduct = convertView.findViewById(R.id.textViewType);
        TextView priceProduct = convertView.findViewById(R.id.price);
        TextView quantityProduct = convertView.findViewById(R.id.quantity);
        TextView quantityOrder = convertView.findViewById(R.id.textViewOrderQuantity);
        TextView priceOrder = convertView.findViewById(R.id.textViewTotalPrice);

//        chuyen du lieu vao customeLayout
        orderId.setText("#"+order.getId());
        orderStatus.setText(order.getStatus());
        nameProduct.setText(orderItem.getName());
        typeProduct.setText(orderItem.getDescription());
        priceProduct.setText(orderItem.getSellingPrice()+"");
        quantityProduct.setText("x"+order.getItemList().size());
        quantityOrder.setText(order.getItemList().size() + " sản phẩm");
        priceOrder.setText("Thành tiền :"+order.getTotalPrice());
        // Sử dụng Glide để tải ảnh
        Uri imageUri = Uri.parse(orderItem.getPath()+"");
        Glide.with(convertView).load(imageUri).into(imgProduct);

        return convertView;
    }
}
