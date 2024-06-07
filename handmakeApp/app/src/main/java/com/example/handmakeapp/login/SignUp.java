package com.example.handmakeapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;
import com.google.firebase.auth.FirebaseAuth;

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
        startActivity(intent);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
