package com.semrekavgaci.bilknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.semrekavgaci.bilknot.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;

    FirebaseFirestore firebaseFirestore;

    EditText emailText;
    EditText passwordFirst;
    EditText passwordSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        emailText = binding.emailTextCA;
        passwordFirst = binding.passwordTextCA;
        passwordSecond = binding.PasswordAgainCA;
    }

    public void returnLoginPageClicked(View view){
        Intent intent = new Intent(this, LogInActivity.class);

        startActivity(intent);
    }

    public void resetPasswordClicked(View view){

    }
}