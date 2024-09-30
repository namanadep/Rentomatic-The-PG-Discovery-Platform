package com.example.rentomatic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button tenantsButton;
    private Button landlordButton;
    private Button developerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tenantsButton = findViewById(R.id.tenantsButton);
        landlordButton = findViewById(R.id.landlordButton);

        tenantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, tenant_login_activity.class);
                startActivity(intent);
            }
        });

        landlordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, landlord_login_activity.class);
                startActivity(intent);
            }
        });
    }
}