package com.example.hwk3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder>{
    private List<Restaurant> restaurants;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewLocation;
        public RatingBar ratingBarRestaurant;
        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewRestaurantName);
            textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);
            ratingBarRestaurant = (RatingBar) view.findViewById(R.id.ratingBarRestaurant);
        }
    } // end MyViewHolder

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.restaurant_recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String name = restaurants.get(position).getName();
        String location = restaurants.get(position).getLocation();
        double rating = restaurants.get(position).getRating();

        holder.textViewName.setText(name);
        holder.textViewLocation.setText(location);
        holder.ratingBarRestaurant.setRating((float)rating);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public void setData(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }


    // other code
    // goes here…
}
