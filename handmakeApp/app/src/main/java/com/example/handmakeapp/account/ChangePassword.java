package com.example.handmakeapp.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;
import com.example.handmakeapp.login.Login;
import com.example.handmakeapp.service.MailService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class ChangePassword extends AppCompatActivity {
    EditText etCodeOTP, passwordOld, passwordNew, passwordNew_repeat;
    Button btn_xacNhan;
    String codeOTP;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    MailService mailService;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        anhXa();
        btn_xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCodeOTP.getVisibility() == View.VISIBLE) {
                    if (etCodeOTP.getText().toString().equals(codeOTP)) {
                        user.updatePassword(password)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            AndroidToast.showToast(ChangePassword.this, "Thay đổi mật khẩu thành công");
                                        }
                                    }
                                });
                    } else {
                        AndroidToast.showToast(ChangePassword.this, "Mã xác nhận không chính xác");
                    }
                } else {
                    if (passwordNew.getText().toString().isEmpty() || passwordOld.getText().toString().isEmpty() ||
                            passwordNew_repeat.getText().toString().isEmpty()) {
                        AndroidToast.showToast(ChangePassword.this, "Vui lòng nhập đầy đủ thông tín!");
                    } else {
                        if(passwordNew.getText().toString().equals(passwordNew_repeat.getText().toString())) {
                            AuthCredential credential = EmailAuthProvider.
                                    getCredential(user.getEmail(), passwordOld.getText().toString());
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                sendEmail(user.getEmail());
                                                password = passwordNew.getText().toString();
                                                etCodeOTP.setVisibility(View.VISIBLE);
                                                passwordOld.setVisibility(View.GONE);
                                                passwordNew.setVisibility(View.GONE);
                                                passwordNew_repeat.setVisibility(View.GONE);
                                            } else {
                                                AndroidToast.showToast(ChangePassword.this, "Mật khẩu cũ không đúng");
                                            }
                                        }
                                    });
                        } else {
                            AndroidToast.showToast(ChangePassword.this, "Mật khẩu mới phải trùng nhau");
                        }
                    }
                }
            }
        });
    }

    private void anhXa() {
        passwordOld = findViewById(R.id.passwordOld);
        passwordNew = findViewById(R.id.passwordNew);
        passwordNew_repeat = findViewById(R.id.passwordNew_repeat);
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