package com.example.goruntu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            } else if (itemId == R.id.search) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));

                return true;
            } else if (itemId == R.id.settings) {

                return true;
            } else if (itemId == R.id.personal) {
                startActivity(new Intent(getApplicationContext(), PersonActivity.class));

                return true;
            }

            return false;
        });
    }
    public void buttonChangePassClicked(View view){
        Intent intent = new Intent(this, ChangePasswordActivity.class);

        startActivity(intent);
    }
    public void buttonChangeProInfoClicked(View view){
        Intent intent = new Intent(this, ChangeProfileInfoActivity.class);

        startActivity(intent);
    }
    public void buttonLogoutClicked(View view){
        Intent intent = new Intent(this, LogInActivity.class);

        startActivity(intent);
    }
    public void settingsToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}