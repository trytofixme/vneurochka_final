package com.example.vneurochka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Message;
import com.example.vneurochka.model.User;
import com.example.vneurochka.view.adapters.MessageAdapter;
import com.example.vneurochka.view.adapters.UserFragmentAdapter;
import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private UserFragmentAdapter userAdapter;
    private ArrayList<User> mUsers;
    private String currentUserId;
    private ArrayList<Message> messageArrayList;
    private ArrayList<Message> currentChatMessages;
    RelativeLayout relative_layout_chat_fragment;
    private String currentGroupId;
    private MessageAdapter adapter;

    public ChatActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentGroupId = extras.getString("currentGroupId");
        }

        initComponents();
        initChatData();
        setupRecycleView();
    }

    private void initChatData() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Messages");
        groupsDatabase.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageArrayList.clear();
                    GenericTypeIndicator<HashMap<String, Message>> indicatorMessages = new GenericTypeIndicator<HashMap<String, Message>>() {};
                    HashMap<String, Message> messageHashMap = snapshot.getValue(indicatorMessages);
                    if (messageHashMap == null) {
                        return;
                    }

                    messageArrayList = (ArrayList<Message>) messageHashMap.values();
                    for (Message message: messageArrayList) {
                        if (Objects.equals(message.getGroupId(), currentGroupId)) {
                            currentChatMessages.add(message);
                        }
                    }

                    adapter.setItems(currentChatMessages);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void initComponents() {
        relative_layout_chat_fragment = this.findViewById(R.id.relative_layout_chat_fragment);
    }

    private void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final RecyclerView recyclerView = this.findViewById(R.id.recycler_view_chat_fragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new MessageAdapter(currentChatMessages, this);
        recyclerView.setAdapter(adapter);
    }
}
