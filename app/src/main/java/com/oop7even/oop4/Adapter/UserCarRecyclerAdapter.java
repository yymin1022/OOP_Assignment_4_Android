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

public class UserCarRecyclerAdapter extends RecyclerView.Adapter<UserCarRecyclerAdapter.ViewHolder>{
    private ArrayList<Car> carList;

    public UserCarRecyclerAdapter(ArrayList<Car> carList){
        this.carList = carList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_user_car, parent, false);

        return new UserCarRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Car")
                .get()
                .addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                for(QueryDocumentSnapshot document : task.getResult()){
                                    if(document.getId().equals(carList.get(position).getNumber())){
                                        String carImage = (String)document.getData().get("img");
                                        String carCompany = carList.get(position).getManufacture();
                                        String carFuel = carList.get(position).getFuel();
                                        String carName = carList.get(position).getName();
                                        String carPrice = String.valueOf(carList.get(position).getPrice());

                                        byte[] imageAsBytes = Base64.decode(carImage.getBytes(), Base64.DEFAULT);
                                        holder.ivCarImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                                        holder.tvCompany.setText(carCompany);
                                        holder.tvFuel.setText(carFuel);
                                        holder.tvName.setText(carName);
                                        holder.tvPrice.setText(carPrice);
                                    }
                                }
                            }
                        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivCarImage;
        TextView tvCompany;
        TextView tvFuel;
        TextView tvName;
        TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCarImage = itemView.findViewById(R.id.recycle_user_image_car);
            tvCompany = itemView.findViewById(R.id.recycle_user_tv_manufacture);
            tvFuel = itemView.findViewById(R.id.recycle_user_tv_fuel);
            tvName = itemView.findViewById(R.id.recycle_user_tv_name);
            tvPrice = itemView.findViewById(R.id.recycle_user_tv_price);
        }
    }
}