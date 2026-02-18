package com.example.smd_a1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;


public class WorkSummary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_work_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        LinearLayout selectedSlotsContainer = findViewById(R.id.selectedSlotsContainer);
        TextView tvWorkoutTitle = findViewById(R.id.tvWorkoutTitle);
        TextView tvNutritionTotal = findViewById(R.id.tvNutritionTotal);
        TextView tvTotal = findViewById(R.id.tvTotal);
        Button btnSendPlan = findViewById(R.id.btnSendPlan);
        Button btnSendSms = findViewById(R.id.btnSendSms);

        String title = intent.getStringExtra("workout_title");
        boolean[] slots = intent.getBooleanArrayExtra("slots");
        float total = intent.getFloatExtra("total", 0);

        tvWorkoutTitle.setText(title);
        tvNutritionTotal.setText("$" + total);
        tvTotal.setText("$" + total);

        String[] names = {"Morning", "Afternoon", "Evening"};

        if (slots != null) {
            for (int i = 0; i < slots.length; i++) {
                if (slots[i]) {
                    TextView tvSlot = new TextView(this);

                    // Copies the exact styling
                    tvSlot.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, tvWorkoutTitle.getTextSize());
                    tvSlot.setTypeface(tvWorkoutTitle.getTypeface());
                    tvSlot.setTextColor(tvWorkoutTitle.getTextColors());

                    tvSlot.setText(names[i]);
                    selectedSlotsContainer.addView(tvSlot);
                }
            }
        }

        btnSendPlan.setOnClickListener(v -> {
            if (title == null || title.isEmpty()) return;

            String shareBody = "Workout Plan: " + title + "\nNutrition Total: $" + total;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

            // createChooser allows user to pick between WhatsApp, Gmail, etc.
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        btnSendSms.setOnClickListener(v -> {
            if (title == null || title.isEmpty() || total <= 0) {
                Toast.makeText(this, "Missing data to send SMS", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        1);
                return;
            }

            try {
                String phoneNumber = "+923020417507"; // Hardcoded number
                String message = "Confirmation: Your " + title + " plan is ready. Total: $" + total;

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                Toast.makeText(this, "SMS Sent!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "SMS Failed. Check permissions.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}