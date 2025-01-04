package com.example.vneurochka.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.vneurochka.R;
import com.example.vneurochka.model.User;
import com.example.vneurochka.view.adapters.ViewPagerAdapter;
import com.example.vneurochka.view.fragments.ChatFragment;
import com.example.vneurochka.view.fragments.UserFragment;
import com.example.vneurochka.viewModel.AuthorizationViewModel;
import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

public class HomeActivity extends AppCompatActivity {
    private AuthorizationViewModel authorizationViewModel;
    private DatabaseViewModel databaseViewModel;

    Toolbar toolbar;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    TextView currentDisplayName;
    ImageView profileImage;
    String username;
    String imageUrl;

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        fetchCurrentUserdata();
        setupPagerFragment();
        onOptionMenuClicked();
    }

    private void setupPagerFragment() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), ViewPagerAdapter.POSITION_UNCHANGED);
        viewPagerAdapter.addFragment(new ChatFragment(this), "Chats");
        viewPagerAdapter.addFragment(new UserFragment(this), "Groups");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void fetchCurrentUserdata() {
        databaseViewModel.fetchingUserDataCurrent();
        databaseViewModel.fetchUserCurrentData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    username = user.getDisplayName();
                    imageUrl = user.getImageUrl();
                    //  Toast.makeText(HomeActivity.this, "Welcome back " + username + ".", Toast.LENGTH_SHORT).show();
                    currentDisplayName.setText(username);
                    if (imageUrl.equals("default")) {
                        profileImage.setImageResource(R.drawable.sample_img);
                    } else {
                        //Glide.with(getApplicationContext()).load(imageUrl).into(profileImage);
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "User not found..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserAuthToSignOut() {
        authorizationViewModel.getFirebaseAuth();
        authorizationViewModel.firebaseAuth.observe(this, new Observer<FirebaseAuth>() {
            @Override
            public void onChanged(FirebaseAuth firebaseAuth) {
                firebaseAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, AuthorizationActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onOptionMenuClicked() {
        toolbar.inflateMenu(R.menu.logout);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.log_out_home) {
                    getUserAuthToSignOut();
                    Toast.makeText(HomeActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            }

        });
    }

    private void init() {
        authorizationViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(AuthorizationViewModel.class);

        toolbar = findViewById(R.id.toolbar);

        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(DatabaseViewModel.class);

        viewPager = findViewById(R.id.view_pager);
        currentDisplayName = findViewById(R.id.tv_display_name);
        profileImage = findViewById(R.id.iv_user_image);
        linearLayout = findViewById(R.id.linearLayout);
        progressBar = findViewById(R.id.progress_bar_home);
        tabLayout = findViewById(R.id.tab_layout);

    }

    private void changeStatus(String status){
        databaseViewModel.addStatusInDatabase("status", status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeStatus("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        changeStatus("offline");
    }
}
