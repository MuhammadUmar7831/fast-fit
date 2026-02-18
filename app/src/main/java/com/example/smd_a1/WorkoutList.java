package com.example.smd_a1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class WorkoutList extends AppCompatActivity {

    LinearLayout mainContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainContainer = findViewById(R.id.cardList);
        ArrayList<Workout> workouts = new ArrayList<Workout>();
        workouts.add(new Workout("Leg Press", "3 sets of 10", R.drawable.leg_press, "https://www.youtube.com/watch?v=qCR9bN3G1t4"));
        workouts.add(new Workout("Lat Pulldown", "3 sets of 12", R.drawable.lat_pulldown, "https://www.youtube.com/watch?v=SALxEARiMkw"));
        workouts.add(new Workout("Chest Press", "15 min", R.drawable.chest_press, "https://www.youtube.com/watch?v=xUm0BiZCWlQ"));

        for (Workout w : workouts) {
            LinearLayout workCard = (LinearLayout) getLayoutInflater().inflate(R.layout.work_card, mainContainer, false);
            ImageView ivImage = workCard.findViewById(R.id.ivImage);
            TextView tvTitle = workCard.findViewById(R.id.workoutTitle);
            TextView tvDetail = workCard.findViewById(R.id.workoutDetail);
            Button btnBookSlot = workCard.findViewById(R.id.btnBookSlot);
            Button btnWatchTutorial = workCard.findViewById(R.id.btnWatchTutorial);

            ivImage.setImageResource(w.imageRes);
            tvTitle.setText(w.getTitle());
            tvDetail.setText(w.getDetails());

            btnBookSlot.setOnClickListener(v -> {
                Intent intent = new Intent(WorkoutList.this, SlotBooking.class);
                intent.putExtra("workout_title", w.getTitle());
                startActivity(intent);
            });

            btnWatchTutorial.setOnClickListener(v->{
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(w.getVideoLink()));
                startActivity(intent);
            });

            mainContainer.addView(workCard);
        }


    }
}