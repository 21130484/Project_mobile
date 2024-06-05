package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {




    private TextView titleTxt, priceTxt, descriptionTxt;
    private Button buyBtn;
    TextView txtshow;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);


        buyBtn = findViewById(R.id.buyBtn);
        txtshow = findViewById(R.id.txtShow);
        Intent intent = getIntent();
        String a = intent.getStringExtra("data");
        txtshow.setText(a);

        ProductDetail p = getIntent().getParcelableExtra("productDetail");

        if(p != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            format.setMaximumFractionDigits(0);
            format.setCurrency(Currency.getInstance("VND"));


            titleTxt = findViewById(R.id.titleText);
            priceTxt = findViewById(R.id.priceText);
            descriptionTxt = findViewById(R.id.contentBody);
            titleTxt.setText(p.getName());
            priceTxt.setText(String.valueOf(format.format(p.getSellingPrice())));
            descriptionTxt.setText(p.getDescription());

        }





        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            BottomDialog bottomDialog = new BottomDialog();
            bottomDialog.show(getSupportFragmentManager(), "TAG");
            }
        });


//Xử lý slide chi tiết ảnh.
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.main_detail_product, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.th_detail_product, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.th1_detail_product, ScaleTypes.FIT));

        ImageSlider imageSlider = findViewById(R.id.image_slider);

        imageSlider.setImageList(imageList);
    }




}