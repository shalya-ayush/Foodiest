package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.MainActivity;
import com.example.automatedfoodorderingsystem.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class UserRegistrationActivity extends AppCompatActivity {
    // XML fields
    TextInputLayout userName, userEmail, userPassword, userPhoneNo;
    Button registerBtn, loginBtn;
    ImageView backPressBtn;
    CountryCodePicker countryCodePicker;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        /////// hooks ////////
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userPhoneNo = findViewById(R.id.user_phoneNo);
        registerBtn = findViewById(R.id.user_register_btn);
        countryCodePicker = findViewById(R.id.country_code);
        loginBtn = findViewById(R.id.user_login_btn);
        backPressBtn = findViewById(R.id.backPress);
        ////// Firebase Hooks ////////
        mAuth = FirebaseAuth.getInstance();

        /////// Back Press Button ///////
        backPressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserRegistrationActivity.this, MainActivity.class));
                finish();
            }
        });

        /////// Register Button /////////
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName() | !validateEmail() | !validatePassword() | !validatePhone()) {
                    return;
                }

                String getUserName = userName.getEditText().getText().toString();
                String getUserEmail = userEmail.getEditText().getText().toString();
                String getUserPassword = userPassword.getEditText().getText().toString();
                String getUserEnteredPhoneNo = userPhoneNo.getEditText().getText().toString();
                String getUserPhoneNo = "+" + countryCodePicker.getFullNumber() + getUserEnteredPhoneNo;
                Intent intent = new Intent(getApplicationContext(), UserAuthenticationActivity.class);
                intent.putExtra("userName", getUserName);
                intent.putExtra("userEmail", getUserEmail);
                intent.putExtra("userPassword", getUserPassword);
                intent.putExtra("userPhoneNo", getUserPhoneNo);
                startActivity(intent);
                finish();
            }
        });

        //////// Login Button ///////////
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserLoginActivity.class));
                finish();
            }
        });
    }

    //////Name Validation //////
    private boolean validateName() {
        String value = userName.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            userName.setError("Field can't be Empty");
            userName.setHintEnabled(false);
            return false;
        } else if (value.length() > 20) {
            userName.setError("Username too large");
            userName.setHintEnabled(false);
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    ///// Email Validation /////
    private boolean validateEmail() {
        String value = userEmail.getEditText().getText().toString();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (value.isEmpty()) {
            userEmail.setError("Field can't be empty");
            userEmail.setHintEnabled(false);
            return false;
        } else if (!value.matches(checkEmail)) {
            userEmail.setError("Invalid Email");
            userEmail.setHintEnabled(false);
            return false;
        } else {
            userEmail.setError(null);
            return true;
        }
    }

    ////// Password Validation ////
    private boolean validatePassword() {
        String value = userPassword.getEditText().getText().toString().trim();
        if (value.length() < 6) {
            userPassword.setError("At least 6 characters required");
            userPassword.setHintEnabled(false);
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }

    ////// Mobile Number Validation //////
    private boolean validatePhone() {
        String value = userPhoneNo.getEditText().getText().toString();
        if (value.length() < 10) {
            userPhoneNo.setError("Invalid Phone Number");
            userPhoneNo.setHintEnabled(false);
            return false;
        } else {

            userPhoneNo.setError(null);
            return true;
        }


    }
}