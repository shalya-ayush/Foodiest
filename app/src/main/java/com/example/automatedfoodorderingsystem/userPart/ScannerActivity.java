package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automatedfoodorderingsystem.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView scannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        Toast.makeText(this, "Please Scan the QR code", Toast.LENGTH_LONG).show();
        scannerView = findViewById(R.id.scannerView);
    }

    @Override
    public void handleResult(Result result) {
        String id = result.getText();
        Intent i = new Intent(ScannerActivity.this, TestActivity.class);
        i.putExtra("id", id);
        startActivity(i);
//        String url = result.getText();
//        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//        startActivity(i);
//        showMenu(url);
        onBackPressed();

    }

    private void showMenu(String url) {
//        FirebaseDatabase.getInstance().getReference().child("RestaurantDatabase").child(url).addValueEventListener()
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        startActivity(new Intent(ScannerActivity.this,EditProfileActivity.class));
//    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}