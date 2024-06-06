package com.example.handmakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home.Home;
import com.example.handmakeapp.service.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class VerifyOTP extends AppCompatActivity {
    ImageView back;
    EditText code;
    Button btnSuccess;
    String email;
    String password;
    TextView sendOTPAgain;
    String codeOTP;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
    }

    private void onClickRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToLogin();
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
}
