package com.example.foodapplication.RestaurantApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RestaurentLoginActivity extends AppCompatActivity {

    EditText Email, password;
    Button SignIn;
    String emailpattren = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurent_login);
        Email = findViewById(R.id.REmailAddress);
        password = findViewById(R.id.Rpasword);
        SignIn = findViewById(R.id.Rlogin);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformLogin();
            }
        });
    }

    private void PerformLogin() {
        String email = Email.getText().toString();
        String pass = password.getText().toString();
        if (!email.matches(emailpattren)) {
            Email.setError("Enter Correct Email.");
        } else if (pass.isEmpty() || pass.length() < 6) {
            password.setError("Enter proper Password.");
        } else {
            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RestaurentLoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(RestaurentLoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                    if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(RestaurentLoginActivity.this, "Login Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RestaurentLoginActivity.this, DishInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}