package com.example.foodapplication.RestaurantApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodapplication.R;
import com.example.foodapplication.RestaurantApp.Adapter.DishAdapter;
import com.example.foodapplication.RestaurantApp.DataModel.Dish;
import com.example.foodapplication.RestaurantApp.DataModel.importDish;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListDish extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DishAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dish);
        recyclerView = findViewById(R.id.recyclerViewDishes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<importDish> options =
                new FirebaseRecyclerOptions.Builder<importDish>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dishes"), importDish.class)
                        .build();

        adapter = new DishAdapter(options);

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start listening for changes in the database
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening to changes in the database
        adapter.stopListening();
    }
}

