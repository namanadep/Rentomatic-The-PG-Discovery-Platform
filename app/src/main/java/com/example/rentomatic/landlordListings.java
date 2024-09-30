    package com.example.rentomatic;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.SearchView;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.firestore.DocumentChange;
    import com.google.firebase.firestore.EventListener;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.FirebaseFirestoreException;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.Locale;

    public class landlordListings extends AppCompatActivity implements RecyclerViewInterface{
        FirebaseAuth auth;
        FirebaseUser user;
        Button logoutButton, createListing;
        androidx.recyclerview.widget.RecyclerView RecyclerView;
        landlordAdapter landlordAdapter;
        ArrayList<itemDetails> itemArrayList;
        private FirebaseFirestore db;
        EditText edittext;
        SearchView searchView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landlord_listings);

            auth = FirebaseAuth.getInstance();
            logoutButton = findViewById(R.id.logoutButton);
            user = auth.getCurrentUser();
            Button createListing = findViewById(R.id.createListing);
            createListing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(landlordListings.this, createListings.class);
                    startActivity(intent);
                }
            });

            RecyclerView = findViewById(R.id.RecyclerView);
            RecyclerView.setHasFixedSize(true);
            RecyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


            db = FirebaseFirestore.getInstance();
            itemArrayList = new ArrayList<itemDetails>();
            landlordAdapter = new landlordAdapter(landlordListings.this, itemArrayList, this);
            RecyclerView.setAdapter(landlordAdapter);

            EventChangeListener();

            searchView = findViewById(R.id.searchView);
            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList(newText);
                    return true;
                }
            });

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(landlordListings.this, landlord_login_activity.class);
                    startActivity(intent);
                    finish();
                }
            });

            if (user == null) {
                Intent intent = new Intent(landlordListings.this, landlord_login_activity.class);
                startActivity(intent);
                finish();
            }
        }

        private void filterList(String text) {
            ArrayList<itemDetails> filteredList = new ArrayList<>();
            for(itemDetails item : itemArrayList){
                if(item.getName().toLowerCase(Locale.getDefault()).contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            if (filteredList.isEmpty()){
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            }else{
                landlordAdapter.setFilteredList(filteredList);
            }
        }
        private void EventChangeListener() {
            db.collection("pgListings")
                    .whereEqualTo("email", user.getEmail())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if(error != null){
                                Log.e("Firestore error", error.getMessage());
                                return;
                            }

                            for(DocumentChange dc : value.getDocumentChanges()){
                                if(dc.getType() == DocumentChange.Type.ADDED){
                                    itemArrayList.add(dc.getDocument().toObject(itemDetails.class));
                                }

                                landlordAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        @Override
        public void onItemClick(int position) {

            Intent intent = new Intent(this, landlord_detailsPage.class);
            intent.putExtra("name", itemArrayList.get(position).getName());
            intent.putExtra("description", itemArrayList.get(position).getDescription());
            intent.putExtra("price", itemArrayList.get(position).getPrice());
            intent.putExtra("location", itemArrayList.get(position).getLocation());
            intent.putExtra("url", itemArrayList.get(position).getImageURL());
            intent.putExtra("amenities", itemArrayList.get(position).getAmenities());
            intent.putExtra("phone", itemArrayList.get(position).getPhone());
            intent.putExtra("position", position);

            startActivity(intent);
        }
        @Override
        public void onBackPressed(){
            super.onBackPressed();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(landlordListings.this, landlord_login_activity.class);
            startActivity(intent);
            finish();
        }
        }