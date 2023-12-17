package com.semrekavgaci.bilknot;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semrekavgaci.bilknot.databinding.RecyclerRow3Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Item3Adapter extends RecyclerView.Adapter<Item3Adapter.Item3Holder> {

    private ArrayList<Item> itemArrayList;

    public Item3Adapter(ArrayList<Item> itemArrayList){
        this.itemArrayList = itemArrayList;
    }

    class Item3Holder extends RecyclerView.ViewHolder{
        RecyclerRow3Binding recyclerRow3Binding;

        public Item3Holder(@NonNull RecyclerRow3Binding recyclerRow3Binding) {
            super(recyclerRow3Binding.getRoot());
            this.recyclerRow3Binding = recyclerRow3Binding;
        }
    }


    @NonNull
    @Override
    public Item3Adapter.Item3Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRow3Binding recyclerRow3Binding = RecyclerRow3Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Item3Adapter.Item3Holder(recyclerRow3Binding);
    }


    @Override
    public void onBindViewHolder(@NonNull Item3Holder holder, int position) {
        holder.recyclerRow3Binding.userNameText.setText(itemArrayList.get(position).userName);
        holder.recyclerRow3Binding.descriptionText.setText(itemArrayList.get(position).description);
        Picasso.get().load(itemArrayList.get(position).downloadUrl).into(holder.recyclerRow3Binding.recyclerviewRowImageview);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
}
