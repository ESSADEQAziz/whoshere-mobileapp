package com.example.qr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardStudent extends AppCompatActivity {


    CardView scanbtn;
    public static TextView scantext;
    CardView signout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_student);

        scantext = (TextView) findViewById(R.id.scantext);
        scanbtn = (CardView) findViewById(R.id.scanbtn);
        signout = (CardView) findViewById(R.id.SignOut);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardStudent.this,LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent HomeIntent = new Intent(Intent.ACTION_MAIN);
        HomeIntent.addCategory(Intent.CATEGORY_HOME);
        HomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(HomeIntent);
    }
    
     public void onProfileClick(View view) {
        startActivity(new Intent(this,Profil.class));
    }


    public void onClick(View view) {
        startActivity(new Intent(this,scannerView.class));
    }

}

