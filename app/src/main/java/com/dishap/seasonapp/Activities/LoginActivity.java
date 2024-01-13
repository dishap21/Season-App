package com.dishap.seasonapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dishap.seasonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView sign_up;
    private Button login;
    private EditText email,pass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        sign_up= findViewById(R.id.SignUpTv);
        login= findViewById(R.id.loginBtn);
        email= findViewById(R.id.loginEmailEt);
        pass= findViewById(R.id.loginPasswordEt);
        sign_up.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==sign_up) {
            Intent startIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(startIntent);
        }
        else if(v==login) {
            String user_email = email.getText().toString();
            String user_password = pass.getText().toString();
            try{
                if(user_email.isEmpty() || user_password.isEmpty()){
                    throw new Exception("All fields must be filled");
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(user_email).matches())){
                    throw new Exception("Invalid Email");
                }
                loginUser(user_email,user_password);

            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mainIntent= new Intent(LoginActivity.this, MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); // The user can't come back to this page
                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_LONG).show();
                            }
                            else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(LoginActivity.this, "Email not exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}