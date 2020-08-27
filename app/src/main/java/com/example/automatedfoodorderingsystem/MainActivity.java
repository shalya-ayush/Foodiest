package com.example.automatedfoodorderingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.restaurantPart.RestaurantLoginActivity;
import com.example.automatedfoodorderingsystem.userPart.UserLoginActivity;

public class MainActivity extends AppCompatActivity {
    private Button user, restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.user);
        restaurant = findViewById(R.id.restaurant);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
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

    }
}