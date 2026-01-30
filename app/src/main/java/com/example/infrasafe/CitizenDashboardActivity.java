package com.example.infrasafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.infrasafe.R;

public class CitizenDashboardActivity extends AppCompatActivity {

    Button btnReportIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_dashboard);

        btnReportIssue = findViewById(R.id.btnReportIssue);

        btnReportIssue.setOnClickListener(v -> {
            startActivity(new Intent(this, ReportIssueActivity.class));
        });

        Button btnViewMap = findViewById(R.id.btnViewMap);

        btnViewMap.setOnClickListener(v -> {
            startActivity(new Intent(this, IssueMapActivity.class));
        });

    }
}
