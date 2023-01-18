package com.example.qr;




import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class LoginActivity extends AppCompatActivity {

     Button btnLogin ;
     EditText password,email;
     FirebaseAuth mauth ;
    FirebaseFirestore db ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.cirLoginButton);
        password = (EditText) findViewById(R.id.Password);
        email = (EditText) findViewById(R.id.Email);
        mauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();





    }

    public void onLoginClick(View View) {

        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    public void onClick(View view) {
      btnLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              LoginUse();
          }

          private void LoginUse() {

              String Email = email.getText().toString().trim();
              String Password = password.getText().toString().trim();


              if (Email.isEmpty()) {
                  email.setError("email is required");
                  email.requestFocus();
                  return;
              }
              if (Password.isEmpty()) {
                  password.setError("Password is required");
                  password.requestFocus();
                  return;
              }
              mauth.signInWithEmailAndPassword(Email, Password)
                      .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                          @Override
                          public void onSuccess(AuthResult authResult) {

                              Toast.makeText(LoginActivity.this, "Welcome to our Application", Toast.LENGTH_SHORT).show();
                              checkUserAccesLevel(authResult.getUser().getUid());

                          }


                      });
          }
      });

    }



          private void SendToDash() {

              Intent intent = new Intent(LoginActivity.this, DashboardTeacher.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);
          }

          @Override
          protected void onStart() {
            
              super.onStart();
              if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                  DocumentReference df = FirebaseFirestore.getInstance().collection ( "Users"). document (FirebaseAuth.getInstance().getCurrentUser().getUid());
                  df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                      @Override
                      public void onSuccess (DocumentSnapshot documentSnapshot) {
                          if (documentSnapshot.getString("isTeacher") != null) {
                              startActivity(new Intent(getApplicationContext(), DashboardTeacher.class));
                              finish();
                          }
                          if (documentSnapshot.getString("isStudent") != null) {
                              startActivity(new Intent(getApplicationContext(), DashboardStudent.class));
                              finish();
                          }
                      }
                      });
                  }
              }

          public void checkUserAccesLevel(String uid) {

              DocumentReference df = db.collection("users").document(uid);
              df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                  @Override
                  public void onSuccess(DocumentSnapshot documentSnapshot) {
                      if (documentSnapshot.getString("isTeacher") != null) {
                          startActivity(new Intent(getApplicationContext(), DashboardTeacher.class));
                          finish();
                      } if(documentSnapshot.getString("isStudent") != null) {
                          startActivity(new Intent(getApplicationContext(), DashboardStudent.class));
                          finish();
                      }

                  }
              });
          }



    }

