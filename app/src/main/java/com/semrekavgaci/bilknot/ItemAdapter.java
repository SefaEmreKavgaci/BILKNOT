package com.semrekavgaci.bilknot;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.semrekavgaci.bilknot.databinding.RecyclerRowBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {

    private OnSavedButtonClickListener savedButtonClickListener;
    private ArrayList<Item> itemArrayList;

    public interface OnSavedButtonClickListener {
        void onSavedButtonClicked(int position);
    }

    public void setOnSavedButtonClickListener(OnSavedButtonClickListener listener) {
        this.savedButtonClickListener = listener;
    }

    public ItemAdapter(ArrayList<Item> itemArrayList){

        this.itemArrayList = itemArrayList;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;

        public ItemHolder(@NonNull RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;

            recyclerRowBinding.saveButton.setOnClickListener(v -> {
                if (savedButtonClickListener != null) {
                    savedButtonClickListener.onSavedButtonClicked(getAdapterPosition());
                }
            });

        }

    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.recyclerRowBinding.userNameText.setText(itemArrayList.get(position).userName);
        holder.recyclerRowBinding.descriptionText.setText(itemArrayList.get(position).description);

        Picasso.get().load(itemArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerviewRowImageview);
        holder.recyclerRowBinding.saveButton.setOnClickListener(v -> {
            if (savedButtonClickListener != null) {
                savedButtonClickListener.onSavedButtonClicked(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
}