package com.example.foodapplication.Cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartAdapter extends FirebaseRecyclerAdapter<CartItem, CartAdapter.CartViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private CartAdapterListener listener;

    public interface CartAdapterListener {
        void onCartItemChanged();
    }

    public CartAdapter(@NonNull FirebaseRecyclerOptions<CartItem> options, CartAdapterListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull CartItem model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        CircleImageView itemImage;
        TextView itemName, itemPrice, itemQuantity;
        Button increaseQuantityButton, decreaseQuantityButton, removeItemButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cartfregmentitemimage);
            itemName = itemView.findViewById(R.id.cartfregmentname);
            itemPrice = itemView.findViewById(R.id.cartfregmentitemprice);
            itemQuantity = itemView.findViewById(R.id.cartfregmentitemquantity);
            increaseQuantityButton = itemView.findViewById(R.id.increase_quantity_button);
            decreaseQuantityButton = itemView.findViewById(R.id.decrease_quantity_button);
            removeItemButton = itemView.findViewById(R.id.remove_item_button);
        }

        private void bind(final CartItem model) {
            itemName.setText(model.getCartname());
            itemPrice.setText(String.format("₹%.2f", model.getCartprice()));
            itemQuantity.setText(String.valueOf(model.getQuantity()));
            Glide.with(itemImage.getContext()).load(model.getCartimageUrl()).into(itemImage);

            increaseQuantityButton.setOnClickListener(v -> {
                adjustItemQuantity(model, 1);
                if (listener != null) {
                    listener.onCartItemChanged();
                }
            });
            decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adjustItemQuantity(model, -1);
                    if (listener != null) {
                        listener.onCartItemChanged();
                    }
                }
            });
            removeItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItemFromCart(model);
                }
            });
        }

        private void adjustItemQuantity(CartItem model, int quantityChange) {
            int newQuantity = model.getQuantity() + quantityChange;
            if (newQuantity > 0) {
                DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("cart").child(model.getCartname());
                itemRef.child("quantity").setValue(newQuantity);
            } else {
                removeItemFromCart(model);
            }
        }

        private void removeItemFromCart(CartItem model) {
            DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("cart").child(model.getCartname());
            itemRef.removeValue();
        }
    }
}
