package com.example.rentomatic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;



public class landlordAdapter extends RecyclerView.Adapter<landlordAdapter.MyViewHolder> {
    FirebaseAuth auth;
    FirebaseUser user;
    Context context;
    ArrayList<itemDetails> itemArrayList;
    private final RecyclerViewInterface recyclerViewInterface;

    public landlordAdapter(Context context, ArrayList<itemDetails> itemArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.itemArrayList = itemArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public void setFilteredList(ArrayList<itemDetails> filteredList) {
        this.itemArrayList = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public landlordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull landlordAdapter.MyViewHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        itemDetails itemDetails = itemArrayList.get(position);
        holder.listing_name.setText(itemDetails.name);
        holder.listing_location.setText(itemDetails.location);
        holder.listing_price.setText(itemDetails.price);
        Glide.with(holder.itemView.getContext())
                .load(itemDetails.getImageURL())
                .into(holder.listing_image);
    }

    public int getItemCount() {
        return itemArrayList.size();
    }

    public static class MyViewHolder extends ViewHolder {
        TextView listing_name, listing_location, listing_price;
        ImageView listing_image;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            listing_location = itemView.findViewById(R.id.listing_location);
            listing_name = itemView.findViewById(R.id.listing_name);
            listing_price = itemView.findViewById(R.id.listing_price);
            listing_image = itemView.findViewById(R.id.listing_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}




