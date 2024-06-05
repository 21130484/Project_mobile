package com.example.handmakeapp.home_products.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.handmakeapp.R;
import com.example.handmakeapp.model.BannerItem;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SliderViewHolder> {
    private List<BannerItem> bannerItems;
    private ViewPager2 viewPager2;

    public SlideAdapter(List<BannerItem> bannerItems, ViewPager2 viewPager2) {
        this.bannerItems = bannerItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.slide_item_container, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImageAndText(bannerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return bannerItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        private TextView tv_title;
        private TextView tv_description;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageBanner);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
        }

        void setImageAndText(BannerItem bannerItem) {
//            imageView.setImageResource(R.drawable.b1);
//            Log.e("check banner", bannerItem.getTitle() + " - " + bannerItem.getImg_path());
            Picasso.get().load(bannerItem.getImg_path()).into(imageView);
            tv_title.setText(bannerItem.getTitle());
            tv_description.setText(bannerItem.getDescription());
        }
    }
}
