package com.example.automatedfoodorderingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.restaurantPart.RestaurantLoginActivity;
import com.example.automatedfoodorderingsystem.userPart.UserDashboardActivity;
import com.example.automatedfoodorderingsystem.userPart.UserRegistrationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    CircleImageView logoImage;
    Button userBtn;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        userBtn = findViewById(R.id.user);
        logoImage = findViewById(R.id.logoImage);

        Button restaurant = findViewById(R.id.restaurant);
        Button skip = findViewById(R.id.skip);

        ////// To set Animation on user button and App logo  //////
        Animation fromRight = AnimationUtils.loadAnimation(this, R.anim.enter_from_right);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
//        logoImage.setAnimation(slideDown);
        userBtn.setAnimation(fromRight);

        //////User Button ////////
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserRegistrationActivity.class));
                finish();
            }
        });

        /////// Restaurant Button /////
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RestaurantLoginActivity.class));
                finish();
            }
        });

        /////// Test Button ////////
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserDashboardActivity.class));
                finish();

            }
        });

        /////// To check if user is already logged in or not ///////
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                if (mUser != null) {
                    sendUserToDashboardActivity();
                    finish();

                }

            }
        };

    }

    ////////// Method to send User to DashBoardActivity ///////////

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