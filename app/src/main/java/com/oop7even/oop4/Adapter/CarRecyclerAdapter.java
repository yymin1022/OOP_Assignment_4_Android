package com.oop7even.oop4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oop7even.oop4.Model.Car;
import com.oop7even.oop4.R;

import java.util.ArrayList;

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.ViewHolder>{
    private ArrayList<Car> carData = null;

    CarRecyclerAdapter(ArrayList<Car> carData){
        this.carData = carData;
    }

    @NonNull
    @Override
    public CarRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_car, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarRecyclerAdapter.ViewHolder holder, int position){
        String carName = carData.get(position).getName();
        holder.tvCarName.setText(carName);
    }

    @Override
    public int getItemCount(){
        return carData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCarName;

        ViewHolder(View itemView){
            super(itemView);

            tvCarName = itemView.findViewById(R.id.recycle_tv_name);
        }
    }
}
