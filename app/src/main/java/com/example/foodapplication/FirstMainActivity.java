package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodapplication.RestaurantApp.RestaurentLoginActivity;
import com.example.foodapplication.UserSideApp.LoginUserActivity;
import com.example.foodapplication.UserSideApp.RegistrationActivity;

public class FirstMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
    }
    public void RestaurentsLogin(View view) {
        startActivities(new Intent[]{new Intent(FirstMainActivity.this, RestaurentLoginActivity.class)});

    }

    public void UserSide(View view) {
        startActivities(new Intent[]{new Intent(FirstMainActivity.this, LoginUserActivity.class)});
    }
}