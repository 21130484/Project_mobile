//package com.example.handmakeapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.handmakeapp.model.Order;
//
//import java.util.ArrayList;
//
//public class CustomAdapterOrder extends BaseAdapter {
//    Context context;
//    ArrayList<Order> arrayList;
//
//    public CustomAdapterOrder(Context context, ArrayList<Order> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.pro_of_order,null);
//
//        Order order = arrayList.get(position);
//        TextView txtDay = convertView.findViewById(R.id.textViewNgay);
//        TextView txtStatus = convertView.findViewById(R.id.textViewStatus);
//        TextView txtMaxTemp = convertView.findViewById(R.id.textViewMaxTemp);
//        TextView txtMinTemp = convertView.findViewById(R.id.textViewMinTemp);
//        ImageView imgStatus = convertView.findViewById(R.id.imageViewStatus);
//
//        txtDay.setText(order.getConsigneeName());
//        txtStatus.setText(order.getConsigneePhoneNumber());
//        txtMaxTemp.setText(order.getAddress());
//        txtMinTemp.setText((int) order.getTotalPrice());
//
////        Picasso.get().load("//cdn.weatherapi.com/weather/64x64/night/"+order.image+".png").into(imgStatus);
//        return convertView;
//    }
//}
