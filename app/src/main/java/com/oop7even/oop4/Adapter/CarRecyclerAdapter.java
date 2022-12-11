package com.oop7even.oop4.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Car")
                .get()
                .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    if(document.getId().equals(carData.get(position).getNumber())){
                                        String carImage = (String)document.getData().get("img");

                                        String carColor = carData.get(position).getColor();
                                        String carDist = String.valueOf(carData.get(position).getDistanceDriven());
                                        String carFuel = carData.get(position).getFuel();
                                        String carManufacture = carData.get(position).getManufacture();
                                        String carName = carData.get(position).getName();
                                        String carPrice = String.valueOf(carData.get(position).getPrice());
                                        String carYear = String.valueOf(carData.get(position).getYear());

                                        byte[] imageAsBytes = Base64.decode(carImage.getBytes(), Base64.DEFAULT);
                                        holder.ivCarImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                                        holder.tvCarColor.setText(carColor);
                                        holder.tvCarDist.setText(carDist);
                                        holder.tvCarFuel.setText(carFuel);
                                        holder.tvCarManufacture.setText(carManufacture);
                                        holder.tvCarName.setText(carName);
                                        holder.tvCarPrice.setText(carPrice);
                                        holder.tvCarYear.setText(carYear);

                                        boolean isAccident = carData.get(position).getIsAccident();
                                        boolean isTune = carData.get(position).getIsTuned();
                                        String carAccidentTune = String.format("사고 %s / 개조 %s", isAccident ? "있음" : "없음", isTune ? "있음" : "없음");
                                        holder.tvCarAccidentTune.setText(carAccidentTune);

                                        break;
                                    }
                                }
                            }
                        });
    }

    @Override
    public int getItemCount(){
        return carData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCarImage;
        TextView tvCarAccidentTune;
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
            tvCarAccidentTune = itemView.findViewById(R.id.recycle_tv_accidenttune);
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
