package com.example.smd_a1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SlotBooking extends AppCompatActivity {

    private Button btnMorning;
    private Button btnAfternoon;
    private Button btnEvening;
    private Button btnProceedToNutrition, btnConfirmWorkout;

    private boolean[] slots = {false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slot_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("workout_title");

        TextView tvWorkoutTitle = findViewById(R.id.tvWorkoutTitle);
        tvWorkoutTitle.setText(title);

        btnProceedToNutrition = findViewById(R.id.btnProceedToNutrition);
        btnConfirmWorkout = findViewById(R.id.btnConfirmWorkout);

        btnMorning = findViewById(R.id.btnMorning);
        btnMorning.setOnClickListener(v -> {
            slots[0] = !slots[0];
            toggleTimeButton(btnMorning, slots[0]);
        });

        btnAfternoon = findViewById(R.id.btnAfternoon);
        btnAfternoon.setOnClickListener(v -> {
            slots[1] = !slots[1];
            toggleTimeButton(btnAfternoon, slots[1]);
        });

        btnEvening = findViewById(R.id.btnEvening);
        btnEvening.setOnClickListener(v -> {
            slots[2] = !slots[2];
            toggleTimeButton(btnEvening, slots[2]);
        });

        btnProceedToNutrition.setOnClickListener(v -> {
            if (shouldNavigate()) {
                Intent _intent = new Intent(SlotBooking.this, Nutrition.class);
                _intent.putExtra("workout_title", title);
                _intent.putExtra("slots", slots);
                startActivity(_intent);
            }
        });

        btnConfirmWorkout.setOnClickListener(v -> {
            if (shouldNavigate()) {
                Intent _intent = new Intent(SlotBooking.this, WorkSummary.class);
                _intent.putExtra("workout_title", title);
                _intent.putExtra("slots", slots);
                _intent.putExtra("total", 0.0);

                startActivity(_intent);
            }
        });
    }

    void toggleTimeButton(Button btn, boolean on) {
        if (on) {
            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_theme_bg));
            btn.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_gray_stroke_white_bg));
            btn.setTextColor(ContextCompat.getColor(this, R.color.black));
        }

        TextView tvSelectedSlots = findViewById(R.id.tvSelectedSlots);
        tvSelectedSlots.setText("Selected Slots: " + getSelectedSlotCount());
    }

    private int getSelectedSlotCount() {
        int count = 0;
        for (boolean slot : slots) {
            if (slot) {
                count++;
            }
        }
        if (count > 0) {
            btnProceedToNutrition.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_theme_bg));
            btnProceedToNutrition.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            btnProceedToNutrition.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_gray_bg));
            btnProceedToNutrition.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        return count;
    }


    private boolean shouldNavigate() {
        for (boolean s : slots) {
            if (s) {
                return true;
            }
        }
        return false;
    }
}