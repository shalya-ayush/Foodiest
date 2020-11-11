package com.example.automatedfoodorderingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.restaurantPart.RestaurantLoginActivity;
import com.example.automatedfoodorderingsystem.userPart.UserDashboardActivity;
import com.example.automatedfoodorderingsystem.userPart.UserRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button user = findViewById(R.id.user);
        Button restaurant = findViewById(R.id.restaurant);
        Button skip = findViewById(R.id.skip);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserRegistrationActivity.class));
                finish();
            }
        });
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RestaurantLoginActivity.class));
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserDashboardActivity.class));
                finish();

            }
        });
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(MainActivity.this, "Verified User", Toast.LENGTH_SHORT).show();
                    sendUserToDashboardActivity();
                    finish();

                }

            }
        };

    }

    private void sendUserToDashboardActivity() {
        Intent intent = new Intent(MainActivity.this, UserDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth != null) {
            mAuth.addAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}