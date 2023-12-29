package com.example.foodapplication.RestaurantApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapplication.FirstMainActivity;
import com.example.foodapplication.R;
import com.example.foodapplication.RestaurantApp.DataModel.Dish;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DishInfoActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView imageView;
    private EditText editTextName, editTextAbout, editTextSpeciality, editTextPrice, editTextDishId;
    private Button buttonBrowse, buttonSave;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_info);
        imageView = findViewById(R.id.dishImage);
        editTextName = findViewById(R.id.dishName);
        editTextAbout = findViewById(R.id.aboutDish);
        editTextSpeciality = findViewById(R.id.specialty);
        editTextPrice = findViewById(R.id.price);
        editTextDishId = findViewById(R.id.IDofDish);
        buttonBrowse = findViewById(R.id.browseImage);
        buttonSave = findViewById(R.id.saveDish);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        buttonBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDishInfo();
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
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void saveDishInfo() {
        String name = editTextName.getText().toString().trim();
        String about = editTextAbout.getText().toString().trim();
        String speciality = editTextSpeciality.getText().toString().trim();
        String priceString = editTextPrice.getText().toString().trim();
        String dishId = editTextDishId.getText().toString().trim();

        if (imageUri != null && !name.isEmpty() && !about.isEmpty() && !speciality.isEmpty() && !priceString.isEmpty() && !dishId.isEmpty()) {
            double price = Double.parseDouble(priceString);
            progressDialog.show(); // Show the ProgressDialog

            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(dishId + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setProgress((int) progress);
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dishes");
                                    Dish dish = new Dish(dishId, name, about, speciality, price, uri.toString());
                                    databaseReference.child(dishId).setValue(dish)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss(); // Dismiss the ProgressDialog
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(DishInfoActivity.this, "Dish info saved successfully", Toast.LENGTH_SHORT).show();
                                                        // Clear the EditText fields
                                                        editTextName.setText("");
                                                        editTextAbout.setText("");
                                                        editTextSpeciality.setText("");
                                                        editTextPrice.setText("");
                                                        editTextDishId.setText("");
                                                        imageView.setImageURI(null);
                                                    } else {
                                                        Toast.makeText(DishInfoActivity.this, "Failed to save dish info", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss(); // Dismiss the ProgressDialog
                            Toast.makeText(DishInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please make sure all fields are filled and an image is selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void Logout(View view) {
        Intent intent = new Intent(DishInfoActivity.this, FirstMainActivity.class);
        Toast.makeText(this, "Loged out", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void DishNext(View view) {
        Intent intent = new Intent(DishInfoActivity.this, ListDish.class);
        startActivity(intent);
    }
}