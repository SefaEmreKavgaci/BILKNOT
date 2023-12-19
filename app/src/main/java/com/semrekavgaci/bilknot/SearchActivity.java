package com.semrekavgaci.bilknot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
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
import com.semrekavgaci.bilknot.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements Item2Adapter.OnSavedButtonClickListener{

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Item> itemArrayList;
    Item2Adapter itemAdapter;
    private ActivitySearchBinding binding;

    private boolean isDateAscending = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        itemArrayList = new ArrayList<>();

        itemAdapter = new Item2Adapter(itemArrayList);

        itemAdapter.setOnSavedButtonClickListener(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.recyclerView.setAdapter(itemAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else if (itemId == R.id.search) {

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
        Item selectedItem = itemArrayList.get(position);

        saveItemToFirestore(selectedItem);

        Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
    }
    private void saveItemToFirestore(Item item) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference savedItemsRef = db.collection(auth.getCurrentUser().getEmail() + " Saved");

        savedItemsRef.add(item);
    }

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection("Items");
        if(!isDateAscending){
            collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Toast.makeText(SearchActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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
        else {
            collectionReference.orderBy("date", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Toast.makeText(SearchActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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
    }

    public void sortByDate(View view){
        isDateAscending = !isDateAscending;

        itemArrayList.clear();
        getData();
    }

    public void searchingButton(View view){
        itemArrayList.clear();

        String searchedWord = binding.searchNote.getText().toString();

        CollectionReference collectionReference = firebaseFirestore.collection("Items");

        if(isDateAscending){
            collectionReference.orderBy("date", Query.Direction.DESCENDING);
        }
        else{
            collectionReference.orderBy("date", Query.Direction.ASCENDING);
        }

        collectionReference.whereEqualTo("description", searchedWord).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(SearchActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
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

    public void searchToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}