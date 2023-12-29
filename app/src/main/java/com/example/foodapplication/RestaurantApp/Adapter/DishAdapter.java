package com.example.foodapplication.RestaurantApp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapplication.R;
import com.example.foodapplication.RestaurantApp.DataModel.importDish;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class DishAdapter extends FirebaseRecyclerAdapter<importDish, DishAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private OnItemClickListener mListener;
    public DishAdapter(@NonNull FirebaseRecyclerOptions<importDish> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull importDish model) {
        holder.dishName.setText(model.getName());
        holder.aboutdish.setText(model.getAbout());
        holder.specialty.setText(model.getSpeciality());
        holder.price.setText(String.format("₹%.2f", model.getPrice()));
        holder.dishid.setText(model.getDishId());
        Glide.with(holder.dishImage.getContext())
                .load(model.getImageUrl())
                .placeholder(R.drawable.bg2)
                .circleCrop()
                .error(R.drawable.bg2)
                .into(holder.dishImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(view, position);
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new myViewHolder(view);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView dishImage;
        TextView dishName, specialty, price,aboutdish,dishid;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            dishImage = itemView.findViewById(R.id.imageViewDish);
            dishName = itemView.findViewById(R.id.textViewDishName);
            aboutdish=itemView.findViewById(R.id.textViewAboutDish);
            specialty = itemView.findViewById(R.id.textViewSpeciality);
            price = itemView.findViewById(R.id.textViewPrice);
            dishid=itemView.findViewById(R.id.textViewId);
        }
    }
}
