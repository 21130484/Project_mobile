package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.graphics.Typeface;
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
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.example.handmakeapp.model.Rate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    /**
     * checkText = id TextView3 => process when no rate.
     */
    private TextView titleTxt, priceTxt, descriptionTxt, rateAvgTxt, rateCountTxt, checkText ;
    private Button buyBtn;
    TextView txtshow;
//    private ImageView imgMain;

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

        /** Rating & Review. START
         * Pair sử dụng để gắn 2 màu với nhau để tạo màu Gradient.
         */
//        RatingReviews ratingReviews = findViewById(R.id.rating_reviews);
//        Pair colors[] = new Pair[] {
//                new Pair<>(Color.parseColor("#0c96c7"), Color.parseColor("#00fe77")),
//                new Pair<>(Color.parseColor("#7b0ab4"), Color.parseColor("#ff069c")),
//                new Pair<>(Color.parseColor("#fe6522"), Color.parseColor("#fdd116")),
//                new Pair<>(Color.parseColor("#104bff"), Color.parseColor("#67cef6")),
//                new Pair<>(Color.parseColor("#ff5d9b"), Color.parseColor("#ffaa69"))
//        };
//
//        int minValue = 30;
//
//        int raters[] = new int[]{
//                minValue + new Random().nextInt(100 - minValue +1),
//                minValue + new Random().nextInt(100 - minValue +1),
//                minValue + new Random().nextInt(100 - minValue +1),
//                minValue + new Random().nextInt(100 - minValue +1),
//                minValue + new Random().nextInt(100 - minValue +1)
//        };
//        ratingReviews.createRatingBars(100, BarLabels.STYPE3, colors, raters);

        if(p != null) {
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            format.setMaximumFractionDigits(0);
            format.setCurrency(Currency.getInstance("VND"));
            titleTxt = findViewById(R.id.titleText);
            priceTxt = findViewById(R.id.priceText);
            descriptionTxt = findViewById(R.id.contentBody);
            rateAvgTxt = findViewById(R.id.rateText);
            rateCountTxt = findViewById(R.id.rateCount);
            checkText = findViewById(R.id.textView3);
            final DecimalFormat df = new DecimalFormat("#.#");
            double count = 0;
            double sum = 0;
            titleTxt.setText(p.getName());
            priceTxt.setText(String.valueOf(format.format(p.getSellingPrice())));
            descriptionTxt.setText(p.getDescription());

            for(Rate r : p.getRateList()) {
                sum += r.getStarRatings();
                count ++;
            }
            sum = sum/count;
            rateCountTxt.setText(String.valueOf(Math.round(count)));
            if(count == 0) {

                rateAvgTxt.setText("Chưa");
                rateAvgTxt.setTypeface(Typeface.DEFAULT);
                checkText.setText("đánh giá");
            }
            else {
                rateAvgTxt.setText(df.format(sum));
            }

            ImageSlider imageSlider = findViewById(R.id.image_slider);
            /**
             * Hiển thị hình ảnh của Product.
             */

            ArrayList<SlideModel> imageList = new ArrayList<>();
            for(Image i : p.getImageList()) {
                String imageUrl = CallAPI.getAbsoluteURL() + "/" + i.getPath();
                imageList.add(new SlideModel(imageUrl, ScaleTypes.FIT));
            }
            imageSlider.setImageList(imageList);
        }
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            BottomDialog bottomDialog = new BottomDialog();
            bottomDialog.show(getSupportFragmentManager(), "TAG");
            }
        });
        }
    }

