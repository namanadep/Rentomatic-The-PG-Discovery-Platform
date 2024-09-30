package com.example.rentomatic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class createListings extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    EditText enterName, enterDescription, enterPrice, enterLocation, enterAmenities, enterPhone, entercoorinates;
    Button submit, upload;
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    private ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listings);

        enterName = findViewById(R.id.enterName);
        imageView = findViewById(R.id.imageView2);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        enterDescription = findViewById(R.id.enterDescription);
        enterPrice = findViewById(R.id.enterPrice);
        enterLocation = findViewById(R.id.enterLocation);
        entercoorinates = findViewById(R.id.entercoordinates);
        upload = findViewById(R.id.enterimg);
        enterAmenities = findViewById(R.id.enterAmenities);
        enterPhone = findViewById(R.id.enterPhone);
        submit = findViewById(R.id.submit);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                String name = enterName.getText().toString();
                String description = enterDescription.getText().toString();
                String price = enterPrice.getText().toString();
                String location = enterLocation.getText().toString();
                String Coordinates = entercoorinates.getText().toString();
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URl = uri.toString();
                        String phone = enterPhone.getText().toString();
                        String amenities = enterAmenities.getText().toString();
                        mAuth = FirebaseAuth.getInstance();
                        user = mAuth.getCurrentUser();
                        String email = user.getEmail();

                        if(name.isEmpty() || description.isEmpty() || price.isEmpty() || location.isEmpty() || Coordinates.isEmpty() || phone.isEmpty() || amenities.isEmpty()){
                            Toast.makeText(createListings.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Map<String,Object> user = new HashMap<>();
                            user.put("name", name);
                            user.put("description", description);
                            user.put("price", price);
                            user.put("location", location);
                            user.put("Coordinates", Coordinates);
                            user.put("imageURL", URl);
                            user.put("phone", phone);
                            user.put("amenities", amenities);
                            user.put("email", email);
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            };

                            db.collection("pgListings")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(createListings.this,"Successful",Toast.LENGTH_SHORT).show();
                                            Handler handler = new Handler();
                                            handler.postDelayed(runnable,1000);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e) {

                                            Toast.makeText(createListings.this,"Failed",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            imageView.setImageURI(filePath);
            SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            String filename = format.format(now);
            storageReference = FirebaseStorage.getInstance().getReference("uploads/"+filename);
            storageReference.putFile(filePath);
        }
    }
}