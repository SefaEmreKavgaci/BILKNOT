package com.semrekavgaci.bilknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
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

                return true;
            }

            return false;
        });
    }
    public void plannerButtonClicked(View view){
        Intent intent = new Intent(this, PlannerActivity.class);

        startActivity(intent);
    }
    public void myNotesClicked(View view){
        Intent intent = new Intent(this, MyNotesActivity.class);

        startActivity(intent);
    }
    public void savedButtonClicked(View view){
        Intent intent = new Intent(this, SavedNotesActivity.class);

        startActivity(intent);
    }
    public void personToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

}