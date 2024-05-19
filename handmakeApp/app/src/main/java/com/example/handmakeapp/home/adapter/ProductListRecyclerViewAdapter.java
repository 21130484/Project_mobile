package com.example.handmakeapp.home.adapter;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.home.mapping.ProductMapping;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder>{
    private List<Product> products;

    public ProductListRecyclerViewAdapter(List<Product> products){
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
        Product product = products.get(position);
        Log.e("product", product.toString());
        new LoadImageTask(product.getId(),holder.imgItem).execute(product.getId());
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getSellingPrice()+"");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
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
    private class LoadImageTask extends AsyncTask<Integer, Void, List<Image>> {
        private int productId;
        private ImageView imgItem;

        public LoadImageTask(int productId, ImageView imgItem) {
            this.productId = productId;
            this.imgItem = imgItem;
        }

        @Override
        protected List<Image> doInBackground(Integer... params) {
            Log.e("product Id", productId + "");
            return ProductMapping.getInstance().getImageByIdProduct(productId);
        }

        @Override
        protected void onPostExecute(List<Image> images) {
            if (images != null && !images.isEmpty()) {
                String imageUrl = CallAPI.getAbsoluteURL() + "/" + images.get(0).getPath();
                Picasso.get().load(imageUrl).into(imgItem);
            }
        }
    }
}
