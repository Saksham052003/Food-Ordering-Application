package com.example.foodapplication.Cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodapplication.MainActivity;
import com.example.foodapplication.R;

public class PlacedOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);
    }

    public void OK(View view) {
        Intent intent=new Intent(PlacedOrder.this, MainActivity.class);
    }
}