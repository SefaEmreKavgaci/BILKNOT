package com.semrekavgaci.bilknot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.semrekavgaci.bilknot.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnSavedButtonClickListener {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Item> itemArrayList;
    ItemAdapter itemAdapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        itemArrayList = new ArrayList<>();

        itemAdapter = new ItemAdapter(itemArrayList);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerView.setAdapter(itemAdapter);


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter.setOnSavedButtonClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
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


    public void onSavedButtonClicked(int position) {
        // Handle the saved button click event
        Item selectedItem = itemArrayList.get(position);

        // Save the item data to Firebase Firestore
        saveItemToFirestore(selectedItem);


        // You can also notify the user that the item is saved
        Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
    }

    private void saveItemToFirestore(Item item) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference savedItemsRef = db.collection("SavedItems");

        // You can use the document ID or a generated ID for each saved item
        // In this example, I'm using a generated ID
        savedItemsRef.add(item);
    }

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection("Items");

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String,Object> data = snapshot.getData();

                        String description = (String) data.get("description");
                        String userName = (String) data.get("userName");
                        String downloadUrl = (String) data.get("downloadurl");
                        Timestamp date = (Timestamp) data.get("date");

                        Item item = new Item(userName, description, downloadUrl,date);

                        itemArrayList.add(item);

                    }

                    itemAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void addNoteClicked(View view){
        Intent intent = new Intent(this, AddNoteActivity.class);

        startActivity(intent);
    }

}