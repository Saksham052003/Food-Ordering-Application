package com.example.foodapplication.Cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapplication.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private RecyclerView recyclerViewCart;
    private CartAdapter adapter;
    private TextView totalPriceTextView;
    private Button proceedToPaymentButton;
    private double totalPrice;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerViewCart = view.findViewById(R.id.cart_recycler_view);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext()));
        totalPriceTextView = view.findViewById(R.id.total_price_text_view);
        proceedToPaymentButton = view.findViewById(R.id.proceed_to_payment_button);

        FirebaseRecyclerOptions<CartItem> options = new FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("cart"), CartItem.class)
                .build();

        adapter = new CartAdapter(options, new CartAdapter.CartAdapterListener() {
            @Override
            public void onCartItemChanged() {
                calculateTotalPrice();
            }
        });

        recyclerViewCart.setAdapter(adapter);

        proceedToPaymentButton.setOnClickListener(v -> proceedToPayment());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        calculateTotalPrice();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void calculateTotalPrice() {
        totalPrice =0.0;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            CartItem item = adapter.getItem(i);
            totalPrice += item.getCartprice() * item.getQuantity();
        }
        totalPriceTextView.setText(String.format("Total: ₹%.2f", totalPrice));

    }

    private void proceedToPayment() {
        Intent intent = new Intent(getActivity(), PlacedOrder.class);
        intent.putExtra("TOTAL_PRICE", totalPrice);
        startActivity(intent);

    }


}