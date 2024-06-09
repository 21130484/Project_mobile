package com.example.handmakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;
import com.example.handmakeapp.service.MailService;

import java.util.Random;

public class ForgotPassword extends AppCompatActivity {
    EditText etCodeOTP, etEmail, etPassword, password, passwordRepeat;
    Button btn_xacNhan;
    String codeOTP, emailRecord;
    MailService mailService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        anhXa();
        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                if (etCodeOTP.getVisibility() == View.GONE || !emailRecord.equals(email)) {
                    emailRecord = email;
                    if (email.isEmpty()) {
                        AndroidToast.showToast(ForgotPassword.this, "Vui lòng nhập Email");
                    } else {
                        sendOTPForgotPassword(etEmail.getText().toString().trim());
                        etCodeOTP.setVisibility(View.VISIBLE);

                    }
                } else if (etCodeOTP.getVisibility() == View.VISIBLE) {
                    String codeOTPEnter = etCodeOTP.getText().toString();
                    if (codeOTPEnter.isEmpty()) {
                        AndroidToast.showToast(ForgotPassword.this, "Vui lòng nhập mã xác nhận");
                    } else {
                        if (codeOTPEnter.equals(codeOTP)) {
                            if (password.getText().toString().equals(passwordRepeat.getText().toString())) {
                                Intent intent = new Intent(ForgotPassword.this, Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                AndroidToast.showToast(ForgotPassword.this, "Password không trùng khớp");
                            }
                        } else {
                            AndroidToast.showToast(ForgotPassword.this, "Mã xác nhận không đúng");
                        }
                    }
                }
            }
        });
    }

    private void anhXa() {
        etEmail = findViewById(R.id.etEmail);
        etCodeOTP = findViewById(R.id.etCodeOTP);
        btn_xacNhan = findViewById(R.id.btn_xacNhan);
    }

    private void sendOTPForgotPassword(String email) {
        sendEmail(email);
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

    public void sendEmail(String email) {
        codeOTP = getRandomCode(6);
        mailService = new MailService(this, email, "Quên mật khẩu", "Chào bạn,<br> Đây là mã xác thực từ Handmadestore, vui lòng không cung cấp mã xác thực cho người khác và chỉ có giá trị sử dụng cho 1 lần: " +
                "<br> <strong>" + codeOTP + "</strong>");
        mailService.execute();
    }

}