package com.example.handmakeapp.detail_product;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;
import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.ProductDetail;
import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test_Activity extends AppCompatActivity {
    private EditText editText;
    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test_detailpr);
        editText = findViewById(R.id.input_id);
        button = findViewById(R.id.submit_id);
        /**
         * Chuyển sang màn hình DetailActivity.
         */
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          int id = Integer.parseInt(editText.getText().toString().trim());
                                          getProductById(id);
                                      }
                                  }
        );
    }
    private void getProductById(int id) {
        CallAPI.api.getPDById("getProductDetailsById", id).enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                Log.e("Kien", "onSuccess");
                ProductDetail p = response.body();
                if(p != null) {
                    Log.e("Kien", p.toString());

                //Chuyển sang DetailActivity truyền ProductDetail.
                    Intent intent = new Intent(Test_Activity.this, DetailActivity.class);
                    intent.putExtra("productDetail", p);
                    startActivity(intent);

                }

                else {
                    Log.e("Kien", response.code()+ "");
                }
            }
            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                Log.e("Kien", "Failure" + t.getMessage(), t);
            }
        });
    }


}
