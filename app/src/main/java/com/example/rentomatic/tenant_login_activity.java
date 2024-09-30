package com.example.rentomatic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

public class tenant_login_activity extends AppCompatActivity {
    TextInputEditText enter_email, enter_password;
    Button reg_button, login_button;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(tenant_login_activity.this, listings.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);

        FirebaseApp.initializeApp(this);
        login_button = findViewById(R.id.login_button);
        reg_button = findViewById(R.id.reg_button);
        mAuth = FirebaseAuth.getInstance();
        enter_email = findViewById(R.id.enter_email);
        enter_password = findViewById(R.id.enter_password);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = enter_email.getText().toString().replaceAll("\\s", "");
                password = String.valueOf(enter_password.getText());

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(tenant_login_activity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(tenant_login_activity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(tenant_login_activity.this, "Login Successful!",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(tenant_login_activity.this, listings.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(tenant_login_activity.this, "Invalid Credentials.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tenant_login_activity.this, tenant_register_activity.class);
                startActivity(intent);
            }
        });
    }
}