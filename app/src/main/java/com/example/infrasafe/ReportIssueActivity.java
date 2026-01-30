package com.example.infrasafe;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infrasafe.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportIssueActivity extends AppCompatActivity {

    Spinner spinnerIssue;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issue);

        spinnerIssue = findViewById(R.id.spinnerIssue);
        btnSubmit = findViewById(R.id.btnSubmit);

        String[] issues = {"Pothole", "Road Crack", "Water Logging", "Broken Streetlight"};
        spinnerIssue.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, issues));

        btnSubmit.setOnClickListener(v -> {
            String issueType = spinnerIssue.getSelectedItem().toString();

            Map<String, Object> issue = new HashMap<>();
            issue.put("issueType", issueType);
            issue.put("status", "Pending");
            issue.put("assignedWorker", "");

            FirebaseFirestore.getInstance()
                    .collection("issues")
                    .add(issue)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this,
                                "Issue Reported Successfully",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    });
        });
    }
}
