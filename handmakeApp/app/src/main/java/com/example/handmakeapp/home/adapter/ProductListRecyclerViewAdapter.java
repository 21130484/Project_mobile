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

import java.util.HashMap;
import java.util.List;

public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder> {
    private List<Product> products;

    HashMap<Integer, List<Image>> productImage;

    public ProductListRecyclerViewAdapter(List<Product> products) {

        this.products = products;
        new LoadImageTask(products).execute();
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
        if (productImage != null && !productImage.isEmpty() && productImage.get(product.getId()) != null) {
            String imageUrl = CallAPI.getAbsoluteURL() + "/" + productImage.get(product.getId()).get(0).getPath();
            Picasso.get().load(imageUrl).into(holder.imgItem);
        }
        holder.txtName.setText(product.getName());
        holder.txtPrice.setText(product.getSellingPrice() + "");
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

    private class LoadImageTask extends AsyncTask<Integer, Void, HashMap<Integer, List<Image>>> {
        List<Product> getIdList;

        public LoadImageTask(List<Product> getIdList) {
            this.getIdList = getIdList;
        }

        @Override
        protected HashMap<Integer, List<Image>> doInBackground(Integer... params) {
            productImage = new HashMap<>();
            for (Product p : getIdList) {
                int productId = p.getId();
                List<Image> images = ProductMapping.getInstance().getImageByIdProduct(productId);
                productImage.put(productId, images);
            }
            return productImage;
        }

        @Override
        protected void onPostExecute(HashMap<Integer, List<Image>> map) {
            if (map != null && !map.isEmpty()) {

            }
        }
    }
}
