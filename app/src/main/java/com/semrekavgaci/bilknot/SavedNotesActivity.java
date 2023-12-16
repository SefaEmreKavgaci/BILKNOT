package com.semrekavgaci.bilknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SavedNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_notes);
    }
    public void toPerson(View view){
        Intent intent = new Intent(this, PersonActivity.class);

        startActivity(intent);
    }
}