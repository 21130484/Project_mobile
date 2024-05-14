package com.example.handmakeapp.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.handmakeapp.R;

public class login extends AppCompatActivity {
    EditText eduser,edpassword;
    Button btnlogin,btnsignup,btnout;
    TextView tvQMK,tvDK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Anhxa();
    }

    private void Anhxa() {
        eduser = (EditText) findViewById(R.id.edittextuser);
        edpassword = (EditText) findViewById(R.id.edittextpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        tvQMK = (TextView) findViewById(R.id.textviewQMK);
        tvDK = (TextView) findViewById(R.id.textviewDK);

    }

}