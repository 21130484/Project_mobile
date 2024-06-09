package com.example.handmakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;

import com.example.handmakeapp.callAPI.CallAPI;
import com.example.handmakeapp.model.Cart;

import com.example.handmakeapp.service.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOTP extends AppCompatActivity {
    ImageView back;
    EditText code;
    Button btnSuccess;
    String email;
    String password, fullName;
    TextView sendOTPAgain;
    String codeOTP;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MailService mailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeforget);
        Anhxa();
        getData();
        sendEmail();
        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeUserEnter = code.getText().toString().trim();
                if (!codeUserEnter.isEmpty()) {
                    if (codeUserEnter.equals(codeOTP)) {
                        onClickRegister(email, password);
                    } else {
                        AndroidToast.showToast(VerifyOTP.this, "Mã OTP không đúng");
                    }
                } else {
                    AndroidToast.showToast(VerifyOTP.this, "Vui lòng nhập mã OTP");
                }
            }
        });

        sendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void Anhxa() {
        back = findViewById(R.id.back);
        code = findViewById(R.id.code);
        btnSuccess = findViewById(R.id.btnSuccess);
        sendOTPAgain = findViewById(R.id.sendOTPAgain);
    }

    private void getData() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        fullName = intent.getStringExtra("fullName");
    }

    private void onClickRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateProfile(user);
                            CallAPI.api.createCart(user.getUid()).enqueue(new Callback<Cart>() {
                                @Override
                                public void onResponse(Call<Cart> call, Response<Cart> response) {
                                    if(response.isSuccessful()) {
                                        response.body();
                                        goToLogin();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Cart> call, Throwable t) {
                                    Log.e("CartRepository", "Failure: " + t.getMessage());
                                    user.delete();
                                }
                            });
                        } else {
                            AndroidToast.showToast(VerifyOTP.this, "Authentication failed.");
                        }
                    }
                });
    }
    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public String getRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public void sendEmail() {
        codeOTP = getRandomCode(6);
        mailService = new MailService(this, email, "Xác thực email", "Chào bạn,<br> Đây là mã xác thực từ Handmadestore, vui lòng không cung cấp mã xác thực cho người khác và chỉ có giá trị sử dụng cho 1 lần: " +
                "<br> <strong>" + codeOTP + "</strong>");
        mailService.execute();
    }

    public void updateProfile(FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("update profile", "User profile updated.");
                        }
                    }
                });
    }
}
