package com.example.handmakeapp.detail_product;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handmakeapp.CurrencyFormatter;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home_products.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home_products.adapter.SlideAdapter;
import com.example.handmakeapp.home_products.mapping.BannerMapping;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.Product;
import com.example.handmakeapp.model.ProductDetail;
import com.example.handmakeapp.model.Rate;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView rv;
    List<Rate> rates;

    ImageView btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        rv = findViewById(R.id.rv_rates);
        btnBack = findViewById(R.id.backArrow);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);

        ProductDetail p = getIntent().getParcelableExtra("ProductDetail");
        rates = p.getRateList();
        Log.e("p in review", p.getName());
        Log.e("rates => ", rates.size()+"");
        rv.setAdapter(new RateListRecyclerViewAdapter(rates));
    }

    private class RateListRecyclerViewAdapter extends RecyclerView.Adapter<RateListRecyclerViewAdapter.MyViewHolder> {
        private List<Rate> rates;

        public RateListRecyclerViewAdapter(List<Rate> rates) {
            this.rates = rates;
        }

        @NonNull
        @Override
        public RateListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rate_item, parent, false);
            return new RateListRecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RateListRecyclerViewAdapter.MyViewHolder holder, int position) {
            Rate rate = rates.get(position);
            holder.rateUser.setText(rate.getUserName());

            long timestampValue =rate.getCreateDate();

            Timestamp timestamp = new Timestamp(timestampValue);

                    holder.rateDate.setText(timestamp.toString());
            holder.rateValue.setText(rate.getStarRatings() + "");
            holder.rateComment.setText(rate.getComment());
        }

        @Override
        public int getItemCount() {
            return rates.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView rateUser;
            private TextView rateDate;
            private TextView rateValue;
            private TextView rateComment;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                rateUser = itemView.findViewById(R.id.rateUser);
                rateDate = itemView.findViewById(R.id.rateDate);
                rateValue = itemView.findViewById(R.id.rateValue);
                rateComment = itemView.findViewById(R.id.rateComment);
            }
        }
    }
}
