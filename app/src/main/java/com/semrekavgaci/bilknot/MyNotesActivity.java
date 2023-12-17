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
import com.semrekavgaci.bilknot.databinding.ActivityMainBinding;
import com.semrekavgaci.bilknot.databinding.ActivityMyNotesBinding;

import java.util.ArrayList;
import java.util.Map;

public class MyNotesActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<Item> itemArrayList;
    Item3Adapter item3Adapter;

    private ActivityMyNotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyNotesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        itemArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        item3Adapter = new Item3Adapter(itemArrayList);

        binding.recyclerView.setAdapter(item3Adapter);

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

    public void getData(){
        CollectionReference collectionReference = firebaseFirestore.collection(auth.getCurrentUser().getEmail());

        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(MyNotesActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if (queryDocumentSnapshots != null) {

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                        Map<String,Object> data = snapshot.getData();

                        String description = (String) data.get("description");
                        String userName = (String) data.get("userName");
                        String downloadUrl = (String) data.get("downloadurl");
                        Timestamp date = (Timestamp) data.get("date");

                        Item item = new Item(userName,description,downloadUrl, date);

                        itemArrayList.add(item);

                    }

                    item3Adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void addItem(View view){
        Intent intent = new Intent(this, AddMyNotesActivity.class);
        startActivity(intent);
    }

    public void toPerson(View view){
        Intent intent = new Intent(this, PersonActivity.class);

        startActivity(intent);
    }
}