package com.oop7even.oop4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.R;

import java.util.ArrayList;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>{
    private final ArrayList<Car> carData;

    public interface OnCarClickListener{
        void onCarClicked(int position, Car clickedCar);
    }

    private OnCarClickListener carClickListener;

    public void setOnCarClickListener(OnCarClickListener listener){
        carClickListener = listener;
    }

    public CarRecyclerAdapter(ArrayList<Car> carData){
        this.carData = carData;
    }

    @NonNull
    @Override
    public CarRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_car, parent, false);
        ViewHolder holder = new ViewHolder(view);

        view.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Car clicked = carData.get(position);
                carClickListener.onCarClicked(position, clicked);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarRecyclerAdapter.ViewHolder holder, int position){
        String carDist = String.valueOf(carData.get(position).getDistanceDriven());
        String carPrice = String.valueOf(carData.get(position).getPrice());
        String carYear = String.valueOf(carData.get(position).getYear());
        String carColor = carData.get(position).getColor();
        String carFuel = carData.get(position).getFuel();
        String carManufacture = carData.get(position).getManufacture();
        String carName = carData.get(position).getName();

        holder.tvCarColor.setText(carColor);
        holder.tvCarDist.setText(carDist);
        holder.tvCarFuel.setText(carFuel);
        holder.tvCarManufacture.setText(carManufacture);
        holder.tvCarName.setText(carName);
        holder.tvCarPrice.setText(carPrice);
        holder.tvCarYear.setText(carYear);
    }

    @Override
    public int getItemCount(){
        return carData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCarImage;
        TextView tvCarColor;
        TextView tvCarDist;
        TextView tvCarFuel;
        TextView tvCarManufacture;
        TextView tvCarName;
        TextView tvCarPrice;
        TextView tvCarYear;

        ViewHolder(View itemView){
            super(itemView);

            ivCarImage = itemView.findViewById(R.id.recycle_image_car);
            tvCarColor = itemView.findViewById(R.id.recycle_tv_color);
            tvCarDist = itemView.findViewById(R.id.recycle_tv_dist);
            tvCarFuel = itemView.findViewById(R.id.recycle_tv_fuel);
            tvCarManufacture = itemView.findViewById(R.id.recycle_tv_manufacture);
            tvCarName = itemView.findViewById(R.id.recycle_tv_name);
            tvCarPrice = itemView.findViewById(R.id.recycle_tv_price);
            tvCarYear = itemView.findViewById(R.id.recycle_tv_year);
        }
    }
}
