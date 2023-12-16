package com.semrekavgaci.bilknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.semrekavgaci.bilknot.databinding.ActivityCreateAccountBinding;
import com.semrekavgaci.bilknot.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
    private  FirebaseAuth auth;

    private ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
    }

    public void loginButtonClicked(View view) {
        String email = binding.emailTextCA.getText().toString();
        String password = binding.passwordTextCA.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(LogInActivity.this, "Email or password can not be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LogInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void forgetPasswordClicked(View view){
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    public void createAccountClicked(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
        finish();
    }
}