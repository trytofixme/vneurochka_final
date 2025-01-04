package com.example.vneurochka.view.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.example.vneurochka.R;
import com.example.vneurochka.model.Chat;
import com.example.vneurochka.model.User;
import com.example.vneurochka.view.adapters.MessageAdapter;
import com.example.vneurochka.view.fragments.BottomSheetProfileDetailUser;
import com.example.vneurochka.viewModel.AuthorizationViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import java.io.IOException;
import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    AuthorizationViewModel authorizationViewModel;
    DatabaseViewModel databaseViewModel;

    ImageView iv_profile_image;
    TextView tv_profile_user_name;
    ImageView iv_back_button;
    ImageView iv_user_status_message_view;

    String profileDisplayName;
    String profileImageURL;
    FirebaseUser currentFirebaseUser;

    EditText et_chat;
    ImageView btn_sendIv;

    String chat;
    String timeStamp;
    String userId_receiver;
    String userId_sender;
    String user_status;
    MessageAdapter messageAdapter;
    ArrayList<Chat> chatsArrayList;
    RecyclerView recyclerView;
    Context context;
    BottomSheetProfileDetailUser bottomSheetProfileDetailUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        userId_receiver = getIntent().getStringExtra("userid");

        init();
        getCurrentFirebaseUser();
        fetchAndSaveCurrentProfileTextAndData();


        iv_profile_image.setOnClickListener(v -> openBottomSheetDetailFragment(profileDisplayName, profileImageURL));


        btn_sendIv.setOnClickListener(v -> {
            chat = et_chat.getText().toString().trim();
            if (!chat.equals("")) {
                addChatInDataBase();
            } else {
                Toast.makeText(MessageActivity.this, "Message can't be empty.", Toast.LENGTH_SHORT).show();
            }
            et_chat.setText("");
        });

    }

    private void openBottomSheetDetailFragment(String username, String imageUrl) {
        bottomSheetProfileDetailUser = new BottomSheetProfileDetailUser(username, imageUrl, context);
        assert getSupportActionBar() != null;
        bottomSheetProfileDetailUser.show(getSupportFragmentManager(), "edit");
    }

    private void getCurrentFirebaseUser() {
        authorizationViewModel.getFirebaseUser();
        authorizationViewModel.firebaseUser.observe(this, firebaseUser -> {
            currentFirebaseUser = firebaseUser;
            userId_sender = currentFirebaseUser.getUid();
        });
    }



    private void fetchAndSaveCurrentProfileTextAndData() {
        if(userId_receiver == null){
            userId_receiver=  getIntent().getStringExtra("userId");
        }
        databaseViewModel.fetchSelectedUserProfileData(userId_receiver);
        databaseViewModel.fetchSelectedProfileUserData.observe(this, dataSnapshot -> {
            User user = dataSnapshot.getValue(User.class);

            assert user != null;
            profileDisplayName = user.getDisplayName();
            profileImageURL = user.getImageUrl();
            user_status = user.getStatus();

            try {
                if (user_status.contains("online") && isNetworkConnected()) {
                    iv_user_status_message_view.setBackgroundResource(R.drawable.online_status);
                } else {
                    iv_user_status_message_view.setBackgroundResource(R.drawable.offline_status);
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

            tv_profile_user_name.setText(profileDisplayName);
            if (profileImageURL.equals("default")) {
                iv_profile_image.setImageResource(R.drawable.sample_img);
            } else {
                //Glide.with(getApplicationContext()).load(profileImageURL).into(iv_profile_image);
            }
            fetchChatFromDatabase(userId_receiver, userId_sender);
        });

        addIsSeen();
    }

    public void addIsSeen() {
        String isSeen = "seen";
        databaseViewModel.fetchChatUser();
        databaseViewModel.fetchedChat.observe(this, (Observer<DataSnapshot>) dataSnapshot -> {
            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                Chat chats = dataSnapshot1.getValue(Chat.class);
                assert chats != null;
                if (chats.getSenderId().equals(userId_receiver) && chats.getReceiverId().equals(userId_sender)) {
                    databaseViewModel.addIsSeenInDatabase(isSeen, dataSnapshot1);
                }
            }

        });

    }


    public boolean isNetworkConnected() throws InterruptedException, IOException {   //check internet connectivity
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    private void fetchChatFromDatabase(String myId, String senderId) {
        databaseViewModel.fetchChatUser();
        databaseViewModel.fetchedChat.observe(this, (Observer<DataSnapshot>) dataSnapshot -> {
            chatsArrayList.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Chat chats = snapshot.getValue(Chat.class);
                assert chats != null;
                if (chats.getReceiverId().equals(senderId) && chats.getSenderId().equals(myId) || chats.getReceiverId().equals(myId) && chats.getSenderId().equals(senderId)) {
                    chatsArrayList.add(chats);
                }

                messageAdapter = new MessageAdapter(chatsArrayList, context, userId_sender);
                recyclerView.setAdapter(messageAdapter);
            }
        });
    }

    private void addChatInDataBase() {

        long tsLong = System.currentTimeMillis();
        timeStamp = Long.toString(tsLong);
        databaseViewModel.addChatDb(userId_receiver, userId_sender, chat, timeStamp);
        databaseViewModel.successAddChatDb.observe(this, aBoolean -> {
            if (aBoolean) {
                // Toast.makeText(MessageActivity.this, "Sent.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MessageActivity.this, "Message can't be sent.", Toast.LENGTH_SHORT).show();
            }
        });

        final String msg = chat;
        databaseViewModel.fetchingUserDataCurrent();
        databaseViewModel.fetchUserCurrentData.observe(this, dataSnapshot -> {
            User users = dataSnapshot.getValue(User.class);
            assert users != null;
        });
    }


    private void init() {
        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(DatabaseViewModel.class);
        authorizationViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(AuthorizationViewModel.class);
        context = MessageActivity.this;

        iv_user_status_message_view = findViewById(R.id.iv_user_status_message_view);
        iv_profile_image = findViewById(R.id.iv_user_image);

        tv_profile_user_name = findViewById(R.id.tv_profile_user_name);
        iv_back_button = findViewById(R.id.iv_back_button);

        iv_back_button.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        et_chat = findViewById(R.id.et_chat);
        btn_sendIv = findViewById(R.id.iv_send_button);

        recyclerView = findViewById(R.id.recycler_view_messages_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        chatsArrayList = new ArrayList<>();
    }

    private void currentUser(String userid){
        SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
        editor.putString("currentuser", userid);
        editor.apply();
    }


    private void addStatusInDatabase(String status) {
        databaseViewModel.addStatusInDatabase("status", status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addStatusInDatabase("online");
        currentUser(userId_receiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        addStatusInDatabase("offline");
        currentUser("none");
    }
}
