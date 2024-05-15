package com.example.handmakeapp.activity_handle.adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.handmakeapp.R;
import com.example.handmakeapp.ServerInformation;
import com.example.handmakeapp.activity_handle.Home;
import com.example.handmakeapp.bean.Image;
import com.example.handmakeapp.bean.Product;
import com.example.handmakeapp.data_api.ProductAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListArrayAdapter extends ArrayAdapter<Product> {
    Activity context;
    int layoutId;
    List<Product> mylist;
    public ProductListArrayAdapter(Activity context, int layoutId, List<Product> mylist) {
        super(context, layoutId, mylist);
        this.context = context;
        this.layoutId = layoutId;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        Product item = mylist.get(position);

//load image: tại sao getId luôn bằng 0?
        ImageView imgItem = convertView.findViewById(R.id.img_item);
        TextView txtName = convertView.findViewById(R.id.txt_name);
        TextView txtPrice = convertView.findViewById(R.id.txt_price);
        new LoadImageTask(imgItem).execute(item.getId());
        txtName.setText(item.getName());
        txtPrice.setText(item.getSellingPrice() + "");

        return convertView;
    }

    private class LoadImageTask extends AsyncTask<Integer, Void, List<Image>> {
        private ImageView imgItem;

        public LoadImageTask(ImageView imgItem) {
            this.imgItem = imgItem;
        }

        @Override
        protected List<Image> doInBackground(Integer... params) {
            int productId = params[0];
            Log.e("product Id", productId + "");
            return ProductAPI.getInstance().getImageByIdProduct(productId);
        }

        @Override
        protected void onPostExecute(List<Image> images) {
            if (images != null && !images.isEmpty()) {
                String imageUrl = ServerInformation.getAbsoluteURL() + "/" + images.get(0).getPath();
                Picasso.get().load(imageUrl).into(imgItem);
            }
        }
    }
}
