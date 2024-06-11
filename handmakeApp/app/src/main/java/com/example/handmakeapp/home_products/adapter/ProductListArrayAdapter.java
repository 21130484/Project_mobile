package com.example.handmakeapp.home_products.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.handmakeapp.CurrencyFormatter;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home_products.Products;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ProductListArrayAdapter extends ArrayAdapter<ProductDetail> {
    Activity context;
    int layoutId;
    List<ProductDetail> mylist;

    public ProductListArrayAdapter(Activity context, int layoutId, List<ProductDetail> mylist) {
        super(context, layoutId, mylist);
        this.context = context;
        this.layoutId = layoutId;
        this.mylist = mylist;
    }

    public void setFilterList(List<ProductDetail> filterList) {
        this.mylist = filterList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (position < mylist.size()) {
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(layoutId, null);
            }

            ProductDetail item = mylist.get(position);
            ImageView imgItem = convertView.findViewById(R.id.img_item);
            TextView txtName = convertView.findViewById(R.id.txt_name);
            TextView txtPrice = convertView.findViewById(R.id.txt_price);

            if (item != null) {
                String imageUrl = item.getImageList().get(0).getPath();
                Picasso.get().load(imageUrl).into(imgItem);
            }
            txtName.setText(item.getName());
            txtPrice.setText(CurrencyFormatter.formatCurrency(item.getSellingPrice()));

            convertView.setVisibility(View.VISIBLE); // Ensure the view is visible
            return convertView;
        } else {
            if (convertView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                convertView = inflater.inflate(layoutId, null);
            }
            convertView.setVisibility(View.GONE); // Hide the view
            return convertView;
        }
    }
}