package com.dishap.seasonapp.Activities;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dishap.seasonapp.Models.Users;
import com.dishap.seasonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    CardView aboutUs, logOut;
    TextView user_username, user_emailId;
    private FirebaseAuth auth;
    String username1;
    String emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        aboutUs = findViewById(R.id.aboutUs);
        logOut = findViewById(R.id.logOut);
        user_username = findViewById(R.id.user_username);
        user_emailId = findViewById(R.id.user_emailId);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null){
            sendToLogin();
        }
        else{
            showUserProfile(firebaseUser);
        }

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToAboutUs = new Intent(ProfileActivity.this, AboutUsActivity.class);
                startActivity(goToAboutUs);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sendToLogin();
            }
        });
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String id = auth.getCurrentUser().getUid();
        emailId = firebaseUser.getEmail();
        user_emailId.setText(emailId);

        //Extract data
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.child(id).child("userName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                username1 = task.getResult().getValue(String.class);
                user_username.setText(username1);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void sendToLogin() {
        Intent sendToLogin = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(sendToLogin);
        Objects.requireNonNull(getActivity(ProfileActivity.this)).finish();
    }

}