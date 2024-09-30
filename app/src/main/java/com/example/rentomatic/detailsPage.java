package com.example.rentomatic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class detailsPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        Button mapBtn = findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = getIntent();
                String Coordinates = intent1.getStringExtra("Coordinates");
                String location = intent1.getStringExtra("location");
            Intent intent = new Intent(detailsPage.this,Gmap.class);
            intent.putExtra("location", location);
            intent.putExtra("Coordinates", Coordinates);
            startActivity(intent);
            }
        });

        Intent intent = getIntent();
        TextView pgName = findViewById(R.id.pgName);
        TextView pgDescription = findViewById(R.id.pgDescription);
        TextView pgPrice = findViewById(R.id.pgPrice);
        TextView pgLocation = findViewById(R.id.pgLocation);
        TextView pgAmenities = findViewById(R.id.pgAmenities);
        ImageView pgImage = findViewById(R.id.pgImage);
        Button contactUS = findViewById(R.id.contactUS);

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
        pgPrice.setText("Rent: ₹" + price);
        pgLocation.setText("LOCATION: \n" + location);
        pgAmenities.setText("AMENITIES: \n" + amenities);
        Glide.with(this)
                .load(url)
                .into(pgImage);
        pgDescription.setMovementMethod(new ScrollingMovementMethod());

        contactUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(detailsPage.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.PHcall:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(phoneno));
                                startActivity(intent);
                                return true;
                            case R.id.Whatsapp:
                                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                                intent1.setData(Uri.parse("https://api.whatsapp.com/send?phone="+phoneno));
                                startActivity(intent1);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
}