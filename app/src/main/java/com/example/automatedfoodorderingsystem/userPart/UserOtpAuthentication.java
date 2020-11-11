package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class UserOtpAuthentication extends AppCompatActivity {
    ImageView cancelBtn;
    EditText mobileNo;
    EditText otp;
    TextView resendOtp;
    Button loginBtn;
    String phoneNo;
    String codeBySystem;
    FirebaseAuth mAuth;
    ////////// Callbacks Method ///////////
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInUserWithCredential(phoneAuthCredential);

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp_authentication);

        ////// Hooks ////////////
        cancelBtn = findViewById(R.id.l_close_btn);
        mobileNo = findViewById(R.id.l_user_mobileNo);
        otp = findViewById(R.id.l_user_otp);
        resendOtp = findViewById(R.id.l_user_resend_otp);
        loginBtn = findViewById(R.id.l_user_login_btn);
        mAuth = FirebaseAuth.getInstance();
        phoneNo = getIntent().getStringExtra("mobileNo");

        sendOTP();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().isEmpty()) {
                    Toast.makeText(UserOtpAuthentication.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                } else if (otp.getText().length() != 6) {
                    Toast.makeText(UserOtpAuthentication.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                } else {
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(codeBySystem, otp.getText().toString());
                    signInUserWithCredential(phoneAuthCredential);
                }
            }
        });

    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,                         //phone number to verify
                60,                             //Timeout duration
                TimeUnit.SECONDS,              //Unit of timeout
                this,                         // Activity (for callback binding)
                mCallbacks);                 // onVerificationStateChangedCallbacks
    }

    private void signInUserWithCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    sendUserToMainActivity();

                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(UserOtpAuthentication.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(UserOtpAuthentication.this, UserDashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}