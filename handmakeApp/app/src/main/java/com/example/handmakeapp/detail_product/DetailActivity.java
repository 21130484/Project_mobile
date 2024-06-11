package com.example.handmakeapp.detail_product;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.cartActivity;
import com.example.handmakeapp.home_products.adapter.ProductListRecyclerViewAdapter;
import com.example.handmakeapp.home_products.mapping.ProductMapping;
import com.example.handmakeapp.model.Category;

import com.example.handmakeapp.model.Image;
import com.example.handmakeapp.model.ProductDetail;
import com.example.handmakeapp.model.Rate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    /**
     * checkText = id TextView3 => process when no rate.
     */
    private TextView titleTxt, priceTxt, descriptionTxt, rateAvgTxt, rateCountTxt, checkText;
    private Button buyBtn;
    TextView txtshow;
    ImageView btnBack;
    ImageView btnCart;
    //    Rating & Review.
    
    private TextView ratingAvgTxt, ratingCountTxt;
    private TextView ratio1, ratio2, ratio3, ratio4, ratio5;
    private ProgressBar pb1, pb2, pb3, pb4, pb5;

    private AppCompatButton ratingWriteBtn;


//   Sản phẩm topp
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        btnBack = findViewById(R.id.imageLArrow);
        ratingWriteBtn = findViewById(R.id.ratingWrite);
        rv = findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(layoutManager);

        new NetworkTask().execute();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
        btnCart = findViewById(R.id.imageCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, cartActivity.class);
                startActivity(i);
            }
        })



        ;


        buyBtn = findViewById(R.id.buyBtn);
        txtshow = findViewById(R.id.txtShow);
        Intent intent = getIntent();
        String a = intent.getStringExtra("data");
        txtshow.setText(a);

        ProductDetail p = getIntent().getParcelableExtra("productDetail");


        if (p != null) {
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

//            Rating & Review declare START.
            double rate1 = 0;
            double rate2 = 0;
            double rate3 = 0;
            double rate4 = 0;
            double rate5 = 0;

            ratingAvgTxt = findViewById(R.id.ratingNumber);
            ratingCountTxt = findViewById(R.id.ratingCount);
            pb1 = findViewById(R.id.rating1);
            pb2 = findViewById(R.id.rating2);
            pb3 = findViewById(R.id.rating3);
            pb4 = findViewById(R.id.rating4);
            pb5 = findViewById(R.id.rating5);
            ratio1 = findViewById(R.id.ratio1);
            ratio2 = findViewById(R.id.ratio2);
            ratio3 = findViewById(R.id.ratio3);
            ratio4 = findViewById(R.id.ratio4);
            ratio5 = findViewById(R.id.ratio5);


            for (Rate r : p.getRateList()) {
                sum += r.getStarRatings();
                switch (r.getStarRatings()) {
                    case 5:
                        rate5++;
                        break;
                    case 4:
                        rate4++;
                        break;
                    case 3:
                        rate3++;
                        break;
                    case 2:
                        rate2++;
                        break;
                    case 1:
                        rate1++;
                        break;
                    default:
                        break;
                }

                count++;
            }
            sum = sum / count;
            int result5 = caculatorPecentage(rate5, count);
            int result4 = caculatorPecentage(rate4, count);
            int result3 = caculatorPecentage(rate3, count);
            int result2 = caculatorPecentage(rate2, count);
            int result1 = restValue(result5, result4, result3, result2);


            rateCountTxt.setText(String.valueOf(Math.round(count)));
            ratingCountTxt.setText(Math.round(count) + " đánh giá");

//            SEt cho progressBar
            pb5.setProgress(result5);
            ratio5.setText(result5 + "%");
            pb4.setProgress(result4);
            ratio4.setText(result4 + "%");
            pb3.setProgress(result3);
            ratio3.setText(result3 + "%");
            pb2.setProgress(result2);
            ratio2.setText(result2 + "%");
            pb1.setProgress(result1);
            ratio1.setText(result1 + "%");

            if (count == 0) {
                pb1.setProgress(0);
                ratio1.setText(0 + "%");
                ratingAvgTxt.setText("0");
                rateAvgTxt.setText("Chưa");
                rateAvgTxt.setTypeface(Typeface.DEFAULT);
                checkText.setText("đánh giá");
                ratingCountTxt.setText("Chưa đánh giá");

            } else {
                rateAvgTxt.setText(df.format(sum));
                ratingAvgTxt.setText(df.format(sum));
            }

            ImageSlider imageSlider = findViewById(R.id.image_slider);
            /**
             * Hiển thị hình ảnh của Product.
             */

            ArrayList<SlideModel> imageList = new ArrayList<>();
            for (Image i : p.getImageList()) {
                String imageUrl = CallAPI.getAbsoluteURL() + "/" + i.getPath();
                imageList.add(new SlideModel(imageUrl, ScaleTypes.FIT));
            }
            imageSlider.setImageList(imageList);
        }
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomDialog bottomDialog = new BottomDialog();
                bottomDialog.setP(p);
                bottomDialog.show(getSupportFragmentManager(), "TAG");
            }
        });

        ratingWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateDialog rateDialog = new RateDialog(DetailActivity.this);
                rateDialog.setP(p);
                rateDialog.setCancelable(false);
                rateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
                rateDialog.show();
            }
        });





    }

    public static int caculatorPecentage(double a, double b) {
        return (int) (a * 100 / b);
    }

    public static int restValue(int a, int b, int c, int d) {
        return 100 - a - b - c - d;
    }

    private class NetworkTask extends AsyncTask<Void, Void, List<ProductDetail>> {
        @Override
        protected List<ProductDetail> doInBackground(Void... voids) {
            List<ProductDetail> recommentProducts = ProductMapping.getInstance().getAllProduct();
            List<Category> t = ProductMapping.getInstance().getCategories();
            Log.e("huhu", recommentProducts.size()+"");
            Log.e("haha", t.size()+"");
            return recommentProducts;
        }

        @Override
        protected void onPostExecute(List<ProductDetail> products) {
            ProductListRecyclerViewAdapter adapter = new ProductListRecyclerViewAdapter(products);
            rv.setAdapter(adapter);
        }
    }
}

