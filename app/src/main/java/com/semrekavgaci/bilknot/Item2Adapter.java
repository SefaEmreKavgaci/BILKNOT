package com.semrekavgaci.bilknot;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.semrekavgaci.bilknot.databinding.RecyclerRow2Binding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Item2Adapter extends RecyclerView.Adapter<Item2Adapter.Item2Holder> {

    private ArrayList<Item> itemArrayList;
    public Item2Adapter(ArrayList<Item> itemArrayList){

        this.itemArrayList = itemArrayList;
    }

    class Item2Holder extends RecyclerView.ViewHolder{
        RecyclerRow2Binding recyclerRow2Binding;

        public Item2Holder(@NonNull RecyclerRow2Binding recyclerRow2Binding) {
            super(recyclerRow2Binding.getRoot());
            this.recyclerRow2Binding = recyclerRow2Binding;
        }
    }


    @NonNull
    @Override
    public Item2Adapter.Item2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRow2Binding recyclerRow2Binding = RecyclerRow2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Item2Adapter.Item2Holder(recyclerRow2Binding);
    }


    @Override
    public void onBindViewHolder(@NonNull Item2Holder holder, int position) {
        holder.recyclerRow2Binding.userNameText.setText(itemArrayList.get(position).userName);
        holder.recyclerRow2Binding.descriptionText.setText(itemArrayList.get(position).description);
        Picasso.get().load(itemArrayList.get(position).downloadUrl).into(holder.recyclerRow2Binding.recyclerviewRowImageview);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }
}
