package com.example.foodapplication.ui.home.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapplication.Cart.CartItem;
import com.example.foodapplication.R;
import com.example.foodapplication.RestaurantApp.Adapter.DishAdapter;
import com.example.foodapplication.ui.home.DataModel.UserSideDish;
import com.example.foodapplication.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishUserAdapter extends FirebaseRecyclerAdapter<UserSideDish,DishUserAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private OnItemClickListener mListener;

    public DishUserAdapter(@NonNull FirebaseRecyclerOptions<UserSideDish> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull DishUserAdapter.myViewHolder holder, int position, @NonNull UserSideDish model) {

        holder.named.setText(model.getName());
        holder.aboutd.setText(model.getAbout());
        holder.priced.setText(String.format("₹%.2f", model.getPrice()));

        Glide.with(holder.imagdiss.getContext())
                .load(model.getImageUrl())
                .placeholder(R.drawable.bg2)
                .circleCrop()
                .error(R.drawable.bg2)
                .into(holder.imagdiss);
        holder.itemView.setOnClickListener(view -> {
            if (mListener != null) {
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(view, pos);
                }
            }
        });
        Button addToCartButton = holder.itemView.findViewById(R.id.add_to_cart_button);
        addToCartButton.setOnClickListener(view -> {
            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart");
            String cartNameKey = model.getName();

            if (cartNameKey != null && !cartNameKey.isEmpty()) {
                CartItem cartItem = new CartItem(cartNameKey, model.getImageUrl(), 0, model.getPrice());
                cartRef.child(cartNameKey).setValue(cartItem)
                        .addOnSuccessListener(aVoid -> {

                            Toast.makeText(holder.itemView.getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(holder.itemView.getContext(), "Failed to add to cart.", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(holder.itemView.getContext(), "Invalid dish name.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public DishUserAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_dish_item, parent, false);
        return new myViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imagdiss;
        TextView named,aboutd,priced;
        Button addToCartButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imagdiss=itemView.findViewById(R.id.Idish);
            named=itemView.findViewById(R.id.Dname);
            aboutd=itemView.findViewById(R.id.AD);
            priced=itemView.findViewById(R.id.DissPrice);


        }
    }
}