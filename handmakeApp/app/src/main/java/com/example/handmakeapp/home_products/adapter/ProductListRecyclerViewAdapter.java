package com.example.handmakeapp.home_products.adapter;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.CurrencyFormatter;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder> {
    private List<ProductDetail> products;

    public ProductListRecyclerViewAdapter(List<ProductDetail> products) {

        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductDetail product = products.get(position);
        if (product != null) {
            String imageUrl = product.getImageList().get(0).getPath();
            Picasso.get().load(imageUrl).into(holder.imgItem);
        }
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(CurrencyFormatter.formatCurrency(product.getSellingPrice()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName;
        private TextView txtPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
            txtName = itemView.findViewById(R.id.txt_name);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}