package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.R;

public class UserRegistrationActivity extends AppCompatActivity {
    private Button registerBtn;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        registerBtn = findViewById(R.id.user_register_btn);
        loginBtn = findViewById(R.id.user_login_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserAuthenticationActivity.class));
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                finish();
            }
        });
    }
}