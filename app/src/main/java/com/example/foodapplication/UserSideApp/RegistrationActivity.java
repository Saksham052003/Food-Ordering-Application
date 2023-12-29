package com.example.foodapplication.UserSideApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapplication.FirstMainActivity;
import com.example.foodapplication.R;
import com.example.foodapplication.UserSideApp.DataModel.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nameEditText, phoneEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private ImageView profileImageView;
    private Button registerButton, uploadImageButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private Uri Uri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("ProfileImages");

        nameEditText = findViewById(R.id.UserNameAddress);
        phoneEditText = findViewById(R.id.UserPhoneNumber);
        emailEditText = findViewById(R.id.UserEmailAddress);
        passwordEditText = findViewById(R.id.userpasword);
        confirmPasswordEditText = findViewById(R.id.ConfirmuserUserPassword);
        profileImageView = findViewById(R.id.userImage);
        registerButton = findViewById(R.id.userbutton);
        uploadImageButton = findViewById(R.id.buttonUploadImage);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri = data.getData();
            profileImageView.setImageURI(Uri);
        }
    }

    private void registerUser() {
        final String name = nameEditText.getText().toString().trim();
        final String phone = phoneEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        if (!email.matches(emailPattern)) {
            emailEditText.setError("Enter a valid email address");
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Password does not match");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && Uri != null) {
                                String userId = user.getUid();
                                StorageReference fileReference = mStorageRef.child(userId + "." + getFileExtension(Uri));
                                fileReference.putFile(Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                User userData = new User(name, phone, email, uri.toString());
                                                mDatabase.child("users").child(phone).setValue(userData);
                                                Toast.makeText(RegistrationActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegistrationActivity.this, LoginUserActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegistrationActivity.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Please select an image.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Authentication failed: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void Signin(View view) {
        startActivities(new Intent[]{new Intent(RegistrationActivity.this, LoginUserActivity.class)});
    }
}