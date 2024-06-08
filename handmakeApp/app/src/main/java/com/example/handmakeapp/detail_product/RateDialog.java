package com.example.handmakeapp.detail_product;

import android.app.Dialog;
import android.content.Context;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.handmakeapp.R;

public class RateDialog extends Dialog {
    private String userName = "K";

    private int rateValue = 0;

    private RatingBar ratingBar;
    private ImageView image;
    public RateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_dialog);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final AppCompatButton submitBtn = findViewById(R.id.submitBtn);
        final AppCompatButton backBtn = findViewById(R.id.backBtn);
        ratingBar = findViewById(R.id.ratingBar);
        image =findViewById(R.id.ratingImage);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating<= 1){

                    image.setImageResource(R.drawable.rate1);
                } else if (rating <= 2) {
                    image.setImageResource(R.drawable.rate2);

                }else if (rating <= 3) {
                    image.setImageResource(R.drawable.rate3);
                }
                else if(rating <=4){
                    image.setImageResource(R.drawable.rate4);

                }
                else if(rating <=5) {
                    image.setImageResource(R.drawable.rate5);
                }

                //animation for image

                animateImage(image);

                rateValue = (int) rating;
            }
        });



    }

    private void animateImage(ImageView ratingImage){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0,1f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(200);
        ratingImage.startAnimation(scaleAnimation);


    }
}
