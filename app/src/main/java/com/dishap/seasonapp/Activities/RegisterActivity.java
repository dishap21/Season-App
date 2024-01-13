package com.dishap.seasonapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dishap.seasonapp.Models.Users;
import com.dishap.seasonapp.R;
import com.dishap.seasonapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button register;
    private TextView login;
    private EditText mail, pass, userNaame;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        register = findViewById(R.id.register);
        login = findViewById(R.id.logintv);
        mail = findViewById(R.id.registerEmail);
        userNaame = findViewById(R.id.registeruserName);
        pass = findViewById(R.id.registerPassword);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            String user_email = mail.getText().toString();
            String user_password = pass.getText().toString();
            String user_name = userNaame.getText().toString();
            try{
                if(user_email.isEmpty() || user_password.isEmpty()){
                    throw new Exception("All fields must be filled");
                }
                if(user_password.length()<6){
                    throw new Exception("Password length should be more than 6 characters");
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(user_email).matches())){
                    throw new Exception("Invalid email");
                }
                registerUser(user_name, user_email,user_password);

            }
            catch (Exception e){
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if(v == login){
            Intent startIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(startIntent);
        }
    }

    private void registerUser(String userName, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Users user = new Users(userNaame.getText().toString(), mail.getText().toString(),
                                    pass.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);

                            Intent mainIntent= new Intent(RegisterActivity.this, LoginActivity.class);
                            Toast.makeText
                                    (RegisterActivity.this, "Login to confirm your registration", Toast.LENGTH_LONG)
                                    .show();
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish(); // The user can't come back to this page
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(RegisterActivity.this, "Email already exists", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}