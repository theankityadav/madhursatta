package com.madhuurstta.wbvjkmatka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DelhiGames extends AppCompatActivity {

    private ImageView back;
    private latobold title;
    private ImageView single;
    private ImageView oddEven;
    private ImageView jodi;

    ArrayList<String> number = new ArrayList<>();
    String market;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delhi_games);
        initViews();
        market = getIntent().getStringExtra("market");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(DelhiGames.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "single")
                        .putExtra("open_av", "1").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        jodi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jodi();
                startActivity(new Intent(DelhiGames.this, single_bet.class).putExtra("market", market).putExtra("list", number).putExtra("game", "jodi").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        oddEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single();
                startActivity(new Intent(DelhiGames.this, OddEven.class).putExtra("open_av", "1").putExtra("market", market).putExtra("list", number).putExtra("game", "single").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    public void jodi() {
        for (int i = 0; i < 100; i++) {
            String temp = String.format("%02d", i);
            number.add(temp);
        }


    }

    public void single() {
        number.clear();
        number.add("0");
        number.add("1");
        number.add("2");
        number.add("3");
        number.add("4");
        number.add("5");
        number.add("6");
        number.add("7");
        number.add("8");
        number.add("9");
    }
    private void initViews() {
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        single = findViewById(R.id.single);
        oddEven = findViewById(R.id.odd_even);
        jodi = findViewById(R.id.jodi);
    }
}