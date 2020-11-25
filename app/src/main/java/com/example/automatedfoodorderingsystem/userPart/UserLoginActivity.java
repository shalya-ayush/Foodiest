package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.R;
import com.hbb20.CountryCodePicker;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserLoginActivity extends AppCompatActivity {
    CircleImageView profileImage;
    CountryCodePicker ccp;
    EditText mobileNumber;
    Button otpBtn;
    Button newUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        ///////// Hooks //////////////
        profileImage = findViewById(R.id.l_user_profile);
        ccp = findViewById(R.id.l_user_ccp);
        mobileNumber = findViewById(R.id.l_user_mobile);
        otpBtn = findViewById(R.id.l_get_otp);
        newUserBtn = findViewById(R.id.l_new_user);
        ccp.registerCarrierNumberEditText(mobileNumber);

        ////// OTP Button /////////
        otpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserOtpAuthentication.class);
                intent.putExtra("mobileNo", ccp.getFullNumberWithPlus().trim());
                startActivity(intent);
                finish();
            }
        });

        ///////New User Button ////////
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this, UserRegistrationActivity.class));
                finish();
            }
        });

    }
}