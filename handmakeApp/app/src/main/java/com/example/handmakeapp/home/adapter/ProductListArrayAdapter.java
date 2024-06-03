package com.example.handmakeapp.home.adapter;

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

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.home.mapping.ProductMapping;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ProductListArrayAdapter extends ArrayAdapter<Product> {
    Activity context;
    int layoutId;
    List<Product> mylist;
    HashMap<Integer, List<Image>> productImage;

    public ProductListArrayAdapter(Activity context, int layoutId, List<Product> mylist) {
        super(context, layoutId, mylist);
        this.context = context;
        this.layoutId = layoutId;
        this.mylist = mylist;
        new LoadImageTask(mylist).execute();
    }

    public void setFilterList(List<Product> filterList) {
        this.mylist = filterList;
        new LoadImageTask(mylist).execute();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Product item = mylist.get(position);

        ImageView imgItem = convertView.findViewById(R.id.img_item);
        TextView txtName = convertView.findViewById(R.id.txt_name);
        TextView txtPrice = convertView.findViewById(R.id.txt_price);


        if (productImage != null && !productImage.isEmpty() && productImage.get(item.getId()) != null) {
            String imageUrl = CallAPI.getAbsoluteURL() + "/" + productImage.get(item.getId()).get(0).getPath();
            Picasso.get().load(imageUrl).into(imgItem);
        }
        txtName.setText(item.getName());
        txtPrice.setText(item.getSellingPrice() + "");

        return convertView;
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