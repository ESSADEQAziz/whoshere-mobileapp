package com.project.qr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.qr.R;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardTeacher extends AppCompatActivity {

CardView studentCard,signout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_teacher);

    studentCard = findViewById(R.id.studentCard);
    studentCard.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DashboardTeacher.this, MainActivity.class);
            startActivity(intent);
        }
    });

        signout = (CardView) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashboardTeacher.this,LoginActivity.class));
                finish();
            }
        });

    }


    public void onClick(View view) {
        startActivity(new Intent(this,QrGenerator.class));
    }
    public void onClick1(View view){
        startActivity(new Intent(this,Profil.class));
    }
    public void onStudentClick(View view){
        startActivity(new Intent(this,MainActivity.class));
    }



    @Override
    public void onBackPressed() {
        Intent HomeIntent = new Intent(Intent.ACTION_MAIN);
        HomeIntent.addCategory(Intent.CATEGORY_HOME);
        HomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(HomeIntent);
    }
}