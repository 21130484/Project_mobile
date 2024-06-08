package com.example.handmakeapp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.AndroidToast;
import com.example.handmakeapp.R;
import com.example.handmakeapp.home_products.Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText etEmail,etPassword;
    Button btnLogin;
    TextView etPasswordForget,signUp;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Anhxa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogin()) {
                    signIn(etEmail.getText().toString().trim(), etPassword.getText().toString().trim());
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
    }

    private void Anhxa() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etPasswordForget = (TextView) findViewById(R.id.etPasswordForget);
        signUp = (TextView) findViewById(R.id.signUp);
        progressDialog = new ProgressDialog(this);
    }

    private void signIn(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            goToHome();
                            finishAffinity();
                        } else {
                            AndroidToast.showToast(Login.this, "Authentication failed.");
                        }
                    }
                });
    }

    private boolean checkLogin() {
        if (etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }

    private void goToHome() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    private void goToRegister() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}