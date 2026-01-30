package com.example.infrasafe;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infrasafe.R;
import com.example.infrasafe.citizen.CitizenDashboardActivity;
import com.example.infrasafe.municipal.MunicipalDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(v -> {

            auth.signInWithEmailAndPassword(
                            etEmail.getText().toString(),
                            etPassword.getText().toString())
                    .addOnSuccessListener(result -> {

                        String uid = auth.getCurrentUser().getUid();

                        db.collection("users").document(uid)
                                .get()
                                .addOnSuccessListener(doc -> {

                                    String role = doc.getString("role");

                                    if (role.equals("citizen")) {
                                        startActivity(new Intent(this,
                                                CitizenDashboardActivity.class));
                                    } else {
                                        startActivity(new Intent(this,
                                                MunicipalDashboardActivity.class));
                                    }
                                    finish();
                                });
                    });
        });

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}
}
