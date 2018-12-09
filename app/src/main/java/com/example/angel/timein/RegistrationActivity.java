/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.youtube.com/playlist?list=PLbte_tgDKVWQOCRIzkgEQ8umdn_S6ZnHr
*/

package com.example.angel.timein;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    //Upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendEmailVerification();
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));

            }
        });
    }

    private void setupUIViews (){
        userName = (EditText) findViewById(R.id.etUserName);
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);
    }
     private Boolean validation(){
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

            if (name.isEmpty() || password.isEmpty() || email.isEmpty()){
                Toast.makeText(RegistrationActivity.this, "Please enter all the details", Toast.LENGTH_LONG).show();
            }
            else{
                result = true;

            }
            return result;
     }

     private void sendEmailVerification(){
         FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
         if(firebaseUser!=null){
             firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(RegistrationActivity.this, "Successfully registered. Verification email has been sent.", Toast.LENGTH_LONG).show();
                         firebaseAuth.signOut();
                         finish();
                         startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                     }
                     else{
                         Toast.makeText(RegistrationActivity.this, "Verification mail hasn't been sent", Toast.LENGTH_LONG).show();

                     }
                 }
             });
         }

     }
}
