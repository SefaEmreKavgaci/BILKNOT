package com.semrekavgaci.bilknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText emailText, newPasswordText, reEnterPasswordText;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.emailTextCA);
        newPasswordText = findViewById(R.id.passwordTextCA);
        reEnterPasswordText = findViewById(R.id.PasswordAgainCA);
        resetPasswordButton = findViewById(R.id.ResetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailText.getText().toString().trim();
        String newPassword = newPasswordText.getText().toString().trim();
        String reEnterPassword = reEnterPasswordText.getText().toString().trim();

        if (email.isEmpty() || newPassword.isEmpty() || reEnterPassword.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPassword.equals(reEnterPassword)) {
            Toast.makeText(ForgotPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(ForgotPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    public void returnLoginPageClicked(View view){
        Intent intent = new Intent(this, LogInActivity.class);

        startActivity(intent);
    }

    public void resetPasswordClicked(View view){
        resetPassword();
    }
}