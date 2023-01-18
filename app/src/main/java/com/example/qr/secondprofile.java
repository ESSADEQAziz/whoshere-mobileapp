package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class secondprofile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondeprofile);
    }
    public void onLoginClick(View view) {
        startActivity(new Intent(this,secondprofile.class));
    }

}