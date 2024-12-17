package com.example.vneurochka.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;

public class GroupsActivity extends AppCompatActivity {

    Button home, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_activity);
        home = findViewById(R.id.home_btn);
        search = findViewById(R.id.search_btn);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(GroupsActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        search.setOnClickListener(v -> {
            Intent intent = new Intent(GroupsActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}
