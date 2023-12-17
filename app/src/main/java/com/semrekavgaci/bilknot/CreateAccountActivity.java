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
import com.semrekavgaci.bilknot.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private String email;
    private String password;
    private String userName;

    private ActivityCreateAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

    }

    public void createAccountButtonClicked(View view){
        String emailText = binding.emailTextCA.getText().toString();
        String passwordText = binding.passwordTextCA.getText().toString();
        // String conPasText = binding.conpas.getText().toString();
        if(!(emailText.equals("")) && !(passwordText.equals(""))){
            auth.createUserWithEmailAndPassword(emailText,passwordText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CreateAccountActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Somethig went wrong, please check the password and email again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void returnLoginPageButtonClicked(View view){
        Intent intent = new Intent(this, LogInActivity.class);

        startActivity(intent);
    }
}