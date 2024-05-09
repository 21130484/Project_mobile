package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.handmakeapp.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
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