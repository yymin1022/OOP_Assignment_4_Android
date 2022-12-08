package com.oop7even.oop4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oop7even.oop4.Model.Accident;
import com.oop7even.oop4.Model.Tune;
import com.oop7even.oop4.R;

import java.util.ArrayList;

public class AccidentTuneRecyclerAdapter extends RecyclerView.Adapter<AccidentTuneRecyclerAdapter.ViewHolder>{
    private final ArrayList<Accident> accidentData;
    private final ArrayList<Tune> tuneData;
    private final boolean isAccident;

    public AccidentTuneRecyclerAdapter(boolean isAccident, ArrayList<Accident> accidentData, ArrayList<Tune> tuneData){
        this.isAccident = isAccident;

        this.accidentData = accidentData;
        this.tuneData = tuneData;
    }

    @NonNull
    @Override
    public AccidentTuneRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_item_car, parent, false);
        AccidentTuneRecyclerAdapter.ViewHolder holder = new AccidentTuneRecyclerAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccidentTuneRecyclerAdapter.ViewHolder holder, int position){
        String content = isAccident ? accidentData.get(position).getContent() : tuneData.get(position).getContent();
        String date = isAccident ? accidentData.get(position).getDate() : tuneData.get(position).getDate();

        holder.tvContent.setText(content);
        holder.tvDate.setText(date);
    }

    @Override
    public int getItemCount(){
        return isAccident ? accidentData.size() : tuneData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvContent;
        TextView tvDate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContent = itemView.findViewById(R.id.recycle_tv_content);
            tvDate = itemView.findViewById(R.id.recycle_tv_date);
        }
    }
}
