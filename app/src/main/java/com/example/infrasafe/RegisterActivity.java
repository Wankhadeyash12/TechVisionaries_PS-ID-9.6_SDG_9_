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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    RadioGroup roleGroup;
    Button btnRegister;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        roleGroup = findViewById(R.id.roleGroup);
        btnRegister = findViewById(R.id.btnRegister);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            int selectedRole = roleGroup.getCheckedRadioButtonId();
            String role = (selectedRole == R.id.rbCitizen)
                    ? "citizen" : "municipal";

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(result -> {

                        String uid = auth.getCurrentUser().getUid();

                        Map<String, Object> user = new HashMap<>();
                        user.put("email", email);
                        user.put("role", role);

                        db.collection("users").document(uid)
                                .set(user)
                                .addOnSuccessListener(unused -> {

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
    }
}
