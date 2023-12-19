package com.semrekavgaci.bilknot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.semrekavgaci.bilknot.databinding.ActivitySavedNotesBinding;

import java.util.ArrayList;
import java.util.Map;

public class SavedNotesActivity extends AppCompatActivity implements Item4Adapter.OnUnsaveButtonClickListener{

    private ArrayList<Item> savedItemsList;
    private Item4Adapter savedItemAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    private ActivitySavedNotesBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        savedItemsList = new ArrayList<>();

        binding = ActivitySavedNotesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        savedItemAdapter = new Item4Adapter(savedItemsList);
        binding.recyclerView.setAdapter(savedItemAdapter);

        savedItemAdapter.setOnUnsaveButtonClickListener(this);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.personal);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else if (itemId == R.id.search) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));

                return true;
            } else if (itemId == R.id.settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                return true;
            } else if (itemId == R.id.personal) {
                startActivity(new Intent(getApplicationContext(), PersonActivity.class));

                return true;
            }

            return false;
        });
        getData();
    }
    public void toPerson(View view){
        Intent intent = new Intent(this, PersonActivity.class);

        startActivity(intent);
    }
    public void onUnsaveButtonClicked(int position) {

        Item selectedItem = savedItemsList.get(position);

        removeItemFromFirestore(selectedItem);
        savedItemsList.clear();

        // You can also notify the user that the item is unsaved
        Toast.makeText(this, "Item unsaved!", Toast.LENGTH_SHORT).show();
    }

    private void removeItemFromFirestore(Item itemId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference savedItemsRef = db.collection(auth.getCurrentUser().getEmail() + " Saved");

        // Find the document with the specified item ID and delete it
        savedItemsRef.whereEqualTo("description", itemId.description)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                    } else {
                        // Handle errors
                        Log.e("SavedNotesActivity", "Error getting documents: ", task.getException());
                    }
                });
    }

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection(auth.getCurrentUser().getEmail() + " Saved");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(SavedNotesActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String,Object> data = snapshot.getData();

                        String description = (String) data.get("description");
                        String userName = (String) data.get("userName");
                        String downloadUrl = (String) data.get("downloadurl");
                        Timestamp date = (Timestamp) data.get("date");

                        Item item = new Item(userName, description, downloadUrl,date);

                        savedItemsList.add(item);

                    }

                    savedItemAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}