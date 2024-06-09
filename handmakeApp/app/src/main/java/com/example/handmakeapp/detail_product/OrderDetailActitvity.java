package com.example.handmakeapp.detail_product;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.handmakeapp.CustomAdapterOrderDetail;
import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Order;
import com.example.handmakeapp.model.OrderItem;
import com.example.handmakeapp.orderHistoryActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActitvity extends AppCompatActivity {

    TextView statusOrder,location,feeShip,totalPrice;
    ListView lvProduct;
    Button btn_huyDon,backDetail;
    boolean updateStatus = false;
    CustomAdapterOrderDetail customAdapterOrderDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail2);
        AnhXa();
        customAdapterOrderDetail.notifyDataSetChanged();
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        statusOrder = findViewById(R.id.statusOrder);
        location = findViewById(R.id.locations);
        lvProduct = findViewById(R.id.lvProduct);
        feeShip = findViewById(R.id.feeShip);
        totalPrice = findViewById(R.id.totalPrice);
        btn_huyDon = findViewById(R.id.btn_huyDon);
        backDetail = findViewById(R.id.backDetail);
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");
//        Sửa trạng thái đơn hàng
        String status = order.getStatus();
        String text2 = "";
        if(status.equalsIgnoreCase("Thành công")){
            text2 = "Đơn hàng đã thành công. Cảm ơn khách hàng!";
        }else if(status.equalsIgnoreCase("Đang giao")){
            text2 = "Đơn hàng đang được giao!";
        }else if(status.equalsIgnoreCase("Chờ xác nhận")){
            text2 = "Đơn hàng đang chờ xác nhận từ cửa hàng!";
            btn_huyDon.setBackgroundColor(getResources().getColor(R.color.red));
            onClickHuyDonHang(order);
        }else if(status.equalsIgnoreCase("Đã hủy")){
            text2 = "Đơn hàng đã bị hủy!";
            statusOrder.setBackgroundColor(getResources().getColor(R.color.red));
        }
        String content = status + "\n" + text2;
        statusOrder.setText(content);
//      Sửa địa chỉ đơn hàng
        String name = order.getConsigneeName();
        String sdt = order.getConsigneePhoneNumber();
        String address = order.getAddress();
        String location_content = name + "\n" + sdt + "\n" + address;
        location.setText(location_content);
//        List product
        customAdapterOrderDetail = new CustomAdapterOrderDetail(OrderDetailActitvity.this, (ArrayList<OrderItem>) order.getItemList());
        lvProduct.setAdapter(customAdapterOrderDetail);
//        Sửa tổng tiền đơn hàng
        double fee = 30000;
        String feeS = String.valueOf(fee);
        feeShip.setText(feeS);
        double total = order.getTotalPrice() + fee;
        String totalP = String.valueOf(total);
        totalPrice.setText(totalP);

    }

    private void onClickHuyDonHang(Order order) {
        btn_huyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAPI.api.updateStatusOrder(order).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        Intent intent = new Intent(OrderDetailActitvity.this, orderHistoryActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.e("Error update status order", t.getMessage());
                    }
                });
            }
        });
    }

}