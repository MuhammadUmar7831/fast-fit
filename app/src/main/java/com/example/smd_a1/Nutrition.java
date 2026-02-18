package com.example.smd_a1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Nutrition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nutrition);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        String title = intent.getStringExtra("workout_title");
        boolean[] slots = intent.getBooleanArrayExtra("slots");

        LinearLayout main = findViewById(R.id.llMain);
        Button btn = findViewById(R.id.btnConfirm);


        ArrayList<_Nutrition> nutritions = new ArrayList<_Nutrition>();
        nutritions.add(new _Nutrition("Protien Shake", R.drawable.protien, 8.99f, 0));
        nutritions.add(new _Nutrition("Energy Bar", R.drawable.energy_bar, 3.49f, 0));
        nutritions.add(new _Nutrition("Salad", R.drawable.salad, 6.99f, 0));
        nutritions.add(new _Nutrition("Juice", R.drawable.juice, 4.99f, 0));

        btn.setOnClickListener(v -> {
            Intent _intent = new Intent(Nutrition.this, WorkSummary.class);
            _intent.putExtra("workout_title", title);
            _intent.putExtra("slots", slots);
            _intent.putExtra("total", calculateTotal(nutritions));

            startActivity(_intent);
        });

        for (_Nutrition n : nutritions) {
            LinearLayout nutritionCard = (LinearLayout) getLayoutInflater().inflate(R.layout.nutrition_card, main, false);

            TextView tvTitle = nutritionCard.findViewById(R.id.tvTitle);
            TextView tvPrice = nutritionCard.findViewById(R.id.tvPrice);
            TextView tvQty = nutritionCard.findViewById(R.id.tvQty);
            TextView tvTotal = findViewById(R.id.tvTotal);
            ImageView ivThumbnail = nutritionCard.findViewById(R.id.ivThumbnail);
            Button btnPlus = nutritionCard.findViewById(R.id.btnPlus);
            MaterialButton btnMinus = nutritionCard.findViewById(R.id.btnMinus);

            tvTitle.setText(n.getName());
            tvPrice.setText("$" + n.getPrice());
            tvQty.setText(String.valueOf(n.getQty()));
            tvTotal.setText("$0");
            ivThumbnail.setImageResource(n.getSrc());

            btnPlus.setOnClickListener(v -> {
                n.setQty(n.getQty() + 1);
                tvQty.setText(String.valueOf(n.getQty()));
                tvTotal.setText("$" + calculateTotal(nutritions));

                if (n.getQty() > 0) {
                    btnMinus.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.theme)));
                }
            });

            btnMinus.setOnClickListener(v -> {
                n.setQty(n.getQty() - 1);
                tvQty.setText(String.valueOf(n.getQty()));
                tvTotal.setText("$" + calculateTotal(nutritions));

                if (n.getQty() == 0) {
                    btnMinus.setBackgroundTintList(ColorStateList.valueOf(
                            androidx.core.content.ContextCompat.getColor(this, android.R.color.darker_gray)
                    ));
                }
            });

            main.addView(nutritionCard);
        }
    }

    private float calculateTotal(ArrayList<_Nutrition> nutritions) {
        float total = 0;
        for (_Nutrition n : nutritions) {
            total += n.getPrice() * n.getQty();
        }
        return total;
    }


    class _Nutrition {
        private String name;
        private int src;
        private float price;
        private int qty;

        public _Nutrition(String name, int src, float price, int qty) {
            this.name = name;
            this.src = src;
            this.price = price;
            this.qty = qty;
        }

        public void setQty(int qty) {
            if (qty >= 0)
                this.qty = qty;
        }

        public String getName() {
            return name;
        }

        public int getSrc() {
            return src;
        }

        public float getPrice() {
            return price;
        }

        public int getQty() {
            return qty;
        }
    }
}