package com.example.vneurochka.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;

public class SearchActivity extends AppCompatActivity {

    Button home_btn, groups_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        home_btn = findViewById(R.id.home_btn);
        groups_btn = findViewById(R.id.groups_btn);
        home_btn.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        groups_btn.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, GroupsActivity.class);
            startActivity(intent);
        });
    }
}
