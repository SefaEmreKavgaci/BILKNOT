package com.semrekavgaci.bilknot;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semrekavgaci.bilknot.databinding.RecyclerRow4Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Item4Adapter extends RecyclerView.Adapter<Item4Adapter.Item4Holder> {

    private ArrayList<Item> itemArrayList;

    public Item4Adapter(ArrayList<Item> itemArrayList){
        this.itemArrayList = itemArrayList;
    }

    class Item4Holder extends RecyclerView.ViewHolder{
        RecyclerRow4Binding recyclerRow4Binding;

        public Item4Holder(@NonNull RecyclerRow4Binding recyclerRow4Binding) {
            super(recyclerRow4Binding.getRoot());
            this.recyclerRow4Binding = recyclerRow4Binding;
        }
    }


    @NonNull
    @Override
    public Item4Adapter.Item4Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRow4Binding recyclerRow4Binding = RecyclerRow4Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Item4Adapter.Item4Holder(recyclerRow4Binding);
    }


    @Override
    public void onBindViewHolder(@NonNull Item4Holder holder, int position) {
        holder.recyclerRow4Binding.userNameText.setText(itemArrayList.get(position).userName);
        holder.recyclerRow4Binding.descriptionText.setText(itemArrayList.get(position).description);
        Picasso.get().load(itemArrayList.get(position).downloadUrl).into(holder.recyclerRow4Binding.recyclerviewRowImageview);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
}
