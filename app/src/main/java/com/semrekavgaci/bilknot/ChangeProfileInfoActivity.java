package com.semrekavgaci.bilknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChangeProfileInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_info);
    }
    public void toSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);

        startActivity(intent);
    }
}