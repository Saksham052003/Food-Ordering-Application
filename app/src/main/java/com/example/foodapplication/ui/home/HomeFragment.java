package com.example.foodapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapplication.R;
import com.example.foodapplication.ui.home.Adapter.DishUserAdapter;
import com.example.foodapplication.ui.home.DataModel.UserSideDish;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerViewDishes;
    private DishUserAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewDishes = view.findViewById(R.id.relview);
        recyclerViewDishes.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<UserSideDish> options =
                new FirebaseRecyclerOptions.Builder<UserSideDish>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("dishes"), UserSideDish.class)
                        .build();
        adapter = new DishUserAdapter(options);
        recyclerViewDishes.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}