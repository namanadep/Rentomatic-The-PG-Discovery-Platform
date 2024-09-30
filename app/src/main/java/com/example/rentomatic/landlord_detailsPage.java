package com.example.rentomatic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class landlord_detailsPage extends AppCompatActivity {

    FirebaseFirestore db;
    landlordAdapter landlordAdapter;
    ArrayList<itemDetails> itemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landlord_details_page);
        db = FirebaseFirestore.getInstance();
        itemArrayList = new ArrayList<itemDetails>();

        Intent intent = getIntent();
        TextView pgName = findViewById(R.id.pgName);
        TextView pgDescription = findViewById(R.id.pgDescription);
        TextView pgPrice = findViewById(R.id.pgPrice);
        TextView pgLocation = findViewById(R.id.pgLocation);
        TextView pgAmenities = findViewById(R.id.pgAmenities);
        ImageView pgImage = findViewById(R.id.pgImage);
        Button delete = findViewById(R.id.delete_listing);

        String url = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String price = intent.getStringExtra("price");
        String location = intent.getStringExtra("location");
        String amenities = intent.getStringExtra("amenities");
        String phone = intent.getStringExtra("phone");
        String phoneno = "tel:" + phone;

        pgName.setText(name);
        pgDescription.setText(description);
        pgPrice.setText("Rent: " + price);
        pgLocation.setText("Location: " + location);
        pgAmenities.setText("Amenities: " + amenities);
        Glide.with(this)
                .load(url)
                .into(pgImage);
        pgDescription.setMovementMethod(new ScrollingMovementMethod());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(landlord_detailsPage.this, "Deleting...", Toast.LENGTH_SHORT).show();
                db.collection("pgListings")
                        .whereEqualTo("name", name)
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful() && !task.getResult().isEmpty()){
                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                    String documentID = documentSnapshot.getId();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    };
                                    Runnable runnable1 = new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                landlordAdapter = null;
                                            } catch (NullPointerException nullPointerException){
                                                //
                                            }
                                            landlordAdapter.notifyDataSetChanged();
                                            }
                                    };
                                    db.collection("pgListings")
                                            .document(documentID)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(landlord_detailsPage.this, "Successfully deleted", Toast.LENGTH_SHORT).show();
                                                    Handler handler = new Handler();
                                                    handler.postDelayed(runnable,1000);
                                                    handler.post(runnable1);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(landlord_detailsPage.this, "Couldn't delete listing", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }else {
                                    Toast.makeText(landlord_detailsPage.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

    }
}