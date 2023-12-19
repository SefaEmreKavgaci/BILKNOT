package com.semrekavgaci.bilknot;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.semrekavgaci.bilknot.databinding.RecyclerRow4Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import com.squareup.picasso.Callback;
public class Item4Adapter extends RecyclerView.Adapter<Item4Adapter.Item4Holder> {

    private ArrayList<Item> itemArrayList;
    private OnUnsaveButtonClickListener unsaveButtonClickListener;

    public interface OnUnsaveButtonClickListener {
        void onUnsaveButtonClicked(int position);
    }

    public void setOnUnsaveButtonClickListener(OnUnsaveButtonClickListener listener) {
        this.unsaveButtonClickListener = listener;
    }

    public Item4Adapter(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    class Item4Holder extends RecyclerView.ViewHolder {
        RecyclerRow4Binding recyclerRow4Binding;

        public Item4Holder(@NonNull RecyclerRow4Binding recyclerRow4Binding) {
            super(recyclerRow4Binding.getRoot());
            this.recyclerRow4Binding = recyclerRow4Binding;

            recyclerRow4Binding.saveButton.setOnClickListener(v -> {
                if (unsaveButtonClickListener != null) {
                    unsaveButtonClickListener.onUnsaveButtonClicked(getAdapterPosition());
                }
            });
        }
    }

    @NonNull
    @Override
    public Item4Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRow4Binding recyclerRow4Binding = RecyclerRow4Binding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new Item4Holder(recyclerRow4Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Item4Holder holder, int position) {
        Item currentItem = itemArrayList.get(position);

        holder.recyclerRow4Binding.userNameText.setText(currentItem.userName);
        holder.recyclerRow4Binding.descriptionText.setText(currentItem.description);

        // Load image with Picasso and handle errors
        Picasso.get()
                .load(currentItem.downloadUrl)
                .into(holder.recyclerRow4Binding.recyclerviewRowImageview, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Item4Adapter", "Image loaded successfully");
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle error (e.g., display a placeholder or show a message)
                        e.printStackTrace();
                    }
                });

        holder.recyclerRow4Binding.saveButton.setOnClickListener(v -> {
            if (unsaveButtonClickListener != null) {
                unsaveButtonClickListener.onUnsaveButtonClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
}