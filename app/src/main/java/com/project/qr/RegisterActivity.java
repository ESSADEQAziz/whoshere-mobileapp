package com.project.qr;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView banner,regesteruse;
    private EditText fullname,mobile,password,email;
    private FirebaseAuth mauth ;
    FirebaseFirestore db ;
    RadioButton Rstudent , Rteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        mauth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        regesteruse = (TextView) findViewById(R.id.cirRegisterButton);
        regesteruse.setOnClickListener(this);
        fullname = (EditText) findViewById(R.id.editTextName);
        mobile = (EditText) findViewById(R.id.editTextMobile);
        password = (EditText) findViewById(R.id.editTextPassword);
        email = (EditText) findViewById(R.id.editTextEmail);

        Rstudent = (RadioButton) findViewById(R.id.RStudent);
        Rteacher = (RadioButton) findViewById(R.id.RTeacher);


    }
    public void backClick(View View) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        switch (view.getId()) {
            case R.id.cirRegisterButton :
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
            break;



        }
    }

    private void regesteruse() {
        String Name = fullname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String Mobile = mobile.getText().toString().trim();
      boolean student = Rstudent.isChecked();
        boolean teacher = Rteacher.isChecked();

        if(Name.isEmpty()){
            fullname.setError("full name is required");
            fullname.requestFocus();
            return;
        }
        if(Email.isEmpty()){
            email.setError("email is required");
            email.requestFocus();
            return;
        }
        if(Password.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(Mobile.isEmpty()){
            mobile.setError("Mobile is required");
            mobile.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            email.setError("please provide valide email");
            email.requestFocus();
            return;
        }
        if(Password.length()<6){
            password.setError("password is very short");
            password.requestFocus();
            return;
        }
        if(student==false && teacher==false){
            Rteacher.setError("please select type");
            Rteacher.requestFocus();
            return;
        }


        mauth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            User user = new User(Name,Mobile,Password,Email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                FirebaseUser userr = mauth.getCurrentUser();
                                                Toast.makeText(RegisterActivity.this, "user has been regestered successfully", Toast.LENGTH_LONG).show();

                                                DocumentReference DF = db.collection("users").document(userr.getUid());
                                                Map<String,Object> userInfo = new HashMap<>();
                                                userInfo.put("fullName",fullname.getText().toString());
                                                userInfo.put("Email",email.getText().toString());
                                                userInfo.put("Mobile phone",mobile.getText().toString());

                                                if(Rstudent.isChecked()){
                                                    userInfo.put("isStudent","1");
                                                }
                                                if(Rteacher.isChecked()){
                                                    userInfo.put("isTeacher","1");
                                                }

                                                 DF.set(userInfo);
                                                SendToLogin();

                                        }
                                            else {
                                                Toast.makeText(RegisterActivity.this, "failed regestered try again", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "failed regestered try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }) ;


    }

    private void SendToLogin() {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }









    @Override
    public void onClick(View v) {
        regesteruse();
    }

    public void onCheckboxClicked(View view) {
    }
}
