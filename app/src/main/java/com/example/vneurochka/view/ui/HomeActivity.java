package com.example.vneurochka.view.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Group;
import com.example.vneurochka.model.User;
import com.example.vneurochka.services.FirebaseInstanceDatabase;
import com.example.vneurochka.view.adapters.ViewPagerItemAdapter;
import com.example.vneurochka.view.fragments.GroupsFragment;
import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private TextView currentDisplayName;
    private ImageView profileImage;
    private ViewPagerItemAdapter viewPagerItemAdapter;
    private DatabaseViewModel databaseViewModel;
    private final List<Group> userGroupList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initControls();
        initListeners();
        onOptionMenuClicked();
        setupViewPager();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent authorizationIntent = new Intent(HomeActivity.this, AuthorizationActivity.class);
            startActivity(authorizationIntent);
            finish();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null)
        {
            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("status").setValue("online");
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null)
        {
            FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getUid()).child("status").setValue("offline");
        }
    }

    private void initControls() {
        currentDisplayName = findViewById(R.id.tv_home_user_name);
        profileImage = findViewById(R.id.iv_home_user_image);
        linearLayout = findViewById(R.id.home_layout);
        progressBar = findViewById(R.id.home_progress_bar);
        toolbar = findViewById(R.id.home_toolbar);

        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(DatabaseViewModel.class);

//        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseViewModel.fetchUserGroups(currentUserId);
//        userGroupList = databaseViewModel.getUsers();
    }

    private void initListeners() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("Users").child(currentUserId);
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);

                    String name = dataSnapshot.child("name").getValue().toString();
                    String imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                    currentDisplayName.setText(name);
                    if (imageUrl.equals("default")) {
                        profileImage.setImageResource(R.drawable.sample_img);
                    } else {
                        // ДОБАВИТЬ ЗАГРУЗКУ ИЗОБРАЖЕНИЕ В ПРЕДСТАВЛЕНИЕ ПО ССЫЛКЕ
                    }
                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, "Пользователь не найден!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException(databaseError.getMessage());
            }
        };
        userDatabase.addValueEventListener(userListener);


    }

    public void onOptionMenuClicked() {
        toolbar.inflateMenu(R.menu.logout);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.log_out_home) {
                // РЕАЛИЗОВАТЬ ВЫХОД ИЗ АККАУНТА
                Toast.makeText(HomeActivity.this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
            }
        });
    }

    private void setupViewPager() {
        viewPagerItemAdapter = new ViewPagerItemAdapter(getSupportFragmentManager());
        //fragmentPagerItemAdapter.addFragment(new ProfileFragment(this), "Профиль");
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseViewModel.fetchUserGroups(currentUserId);
        viewPagerItemAdapter.addFragment(new GroupsFragment(), "Мои группы");

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewPagerItemAdapter);

        TabLayout tabLayout = findViewById(R.id.home_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
