package com.dishap.seasonapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dishap.seasonapp.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().hide();
    }
}