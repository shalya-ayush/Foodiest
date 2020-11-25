package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.UserFragments.FragmentHome;
import com.example.automatedfoodorderingsystem.UserFragments.FragmentPay;
import com.example.automatedfoodorderingsystem.UserFragments.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserDashboardActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.user_home:
                    selectedFragment = new FragmentHome();
                    break;
                case R.id.user_menu:
                    selectedFragment = null;
                    startActivity(new Intent(UserDashboardActivity.this, ScannerActivity.class));
                    break;
                case R.id.user_pay:
                    selectedFragment = new FragmentPay();
                    break;
                case R.id.user_profile:
                    selectedFragment = new FragmentProfile();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        BottomNavigationView bottomNavigationView = findViewById(R.id.user_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();


    }
}