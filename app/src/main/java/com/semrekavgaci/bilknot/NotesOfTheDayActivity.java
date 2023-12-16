package com.semrekavgaci.bilknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotesOfTheDayActivity extends AppCompatActivity {

    private TextView dayTextView;
    private EditText notesEditText;
    private String selectedDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_of_the_day);
        dayTextView = findViewById(R.id.dateView);
        notesEditText = findViewById(R.id.editText);


        if (getIntent().hasExtra("selectedDay")) {
            selectedDateString = getIntent().getStringExtra("selectedDay");
            LocalDate selectedDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                selectedDate = LocalDate.parse(selectedDateString);
            }

            // Format the date to display in the TextView
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            }
            String formattedDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formattedDate = selectedDate.format(formatter);
            }

            dayTextView.setText(formattedDate);

            String notes = loadNotes(selectedDateString);
            notesEditText.setText(notes);
        }
    }
    public void backClicked(View view){
        Intent intent = new Intent(this, PlannerActivity.class);
        String notes = notesEditText.getText().toString();
        saveNotes(selectedDateString, notes);

        startActivity(intent);
    }

    private String loadNotes(String date) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyNotes", MODE_PRIVATE);
        return sharedPreferences.getString(date, "");
    }

    private void saveNotes(String date, String notes) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyNotes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(date, notes);
        editor.apply();
    }

}