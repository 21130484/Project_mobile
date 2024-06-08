package com.example.handmakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    EditText fullName, etEmail, password, passwordRepeat;
    Button btnRegister;
    TextView login;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Anhxa();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkRegister()) {
                    goToOTPCode();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    private void Anhxa() {
        fullName = findViewById(R.id.fullName);
        etEmail = findViewById(R.id.etEmail);
        password = findViewById(R.id.password);
        passwordRepeat = findViewById(R.id.passwordRepeat);
        btnRegister = findViewById(R.id.btnRegister);
        login = findViewById(R.id.login);
    }

    private boolean checkRegister() {
        if (fullName.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()
        || password.getText().toString().isEmpty() || passwordRepeat.getText().toString().isEmpty()) {
            return false;
        }

        if (!password.getText().toString().equals(passwordRepeat.getText().toString())) {
            return false;
        }

        return true;
    }


    private void goToOTPCode() {
        Intent intent = new Intent(this, VerifyOTP.class);
        intent.putExtra("email", etEmail.getText().toString().trim());
        intent.putExtra("password", password.getText().toString().trim());
        intent.putExtra("fullName", fullName.getText().toString());
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
