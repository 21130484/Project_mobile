package com.example.handmakeapp.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.handmakeapp.R;
import com.example.handmakeapp.login.Login;
import com.example.handmakeapp.service.MailService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Random;

public class ManagerAccount extends AppCompatActivity {
    private EditText etFullname;
    private TextView  etEmail;
    private Button btnSave, btnLogout;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_account);
        anhXa();

        if (user != null) {
            etEmail.setText(user.getEmail());
            etFullname.setText(user.getDisplayName());
        }
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                goToLogin();
                finishAffinity();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changeInfor(etFullname.getText().toString())) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void anhXa() {
        etEmail = findViewById(R.id.etEmail);
        etFullname = findViewById(R.id.etFullname);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private boolean changeInfor(String fullName) {
        boolean re = false;
        if (fullName != user.getDisplayName()) {
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
            etFullname.setText(fullName);
            re = true;
        }

        return re;
    }

}