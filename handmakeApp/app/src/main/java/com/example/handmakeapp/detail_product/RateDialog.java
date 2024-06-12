package com.example.handmakeapp.detail_product;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.ProductDetail;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateDialog extends Dialog {

    private ProductDetail p;

    public void setP(ProductDetail p) {
        this.p = p;
    }

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    private String userName = "K";

    private int rateValue = 3;
    private EditText commentTxt;

    private RatingBar ratingBar;
    private ImageView image;

    public RateDialog(@NonNull Activity activity) {
        super(activity);
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
        commentTxt = findViewById(R.id.textArea_information);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating<= 1){
                    rateValue =1;

                    image.setImageResource(R.drawable.rate1);
                } else if (rating <= 2) {
                    image.setImageResource(R.drawable.rate2);
                    rateValue = 2;
                }else if (rating <= 3) {
                    image.setImageResource(R.drawable.rate3);
                    rateValue = 3;
                }
                else if(rating <=4){
                    image.setImageResource(R.drawable.rate4);
                    rateValue = 4;
                }
                else if(rating <=5) {
                    image.setImageResource(R.drawable.rate5);
                    rateValue =5;
                }

                //animation for image

                animateImage(image);

                rateValue = (int) rating;
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRatings();
                getOwnerActivity().recreate();
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

    private void addRatings(){
        String userId = user.getUid();
        String fullName = user.getDisplayName();
        int pid = p.getId();
        String comment = commentTxt.getText().toString();

        Call<Void> call=  CallAPI.api.addRatings(pid, userId, rateValue, comment, fullName );

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    showThankYouDialog();
            }
                else {
                    Toast.makeText(getContext(), "Lỗi xảy ra khi thêm", Toast.LENGTH_SHORT).show();
                    Log.e("RATING", response.message());
                }
                }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showThankYouDialog();
            }
        });

    }

    private void showThankYouDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Cảm ơn bạn đã đánh giá!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dismiss(); // Dismiss the RateDialog
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
