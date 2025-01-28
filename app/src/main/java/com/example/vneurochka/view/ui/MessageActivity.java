package com.example.vneurochka.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vneurochka.R;
import com.example.vneurochka.model.Message;
import com.example.vneurochka.view.adapters.MessageFragmentAdapter;
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
import java.util.concurrent.atomic.AtomicReference;

public class MessageActivity extends AppCompatActivity {
    private String currentUserId;
    private ArrayList<Message> messageArrayList = new ArrayList<>();
    private ArrayList<Message> currentChatMessages = new ArrayList<>();
    RelativeLayout relative_layout_chat_fragment;
    private String currentGroupId;
    private MessageFragmentAdapter adapter;
    private ImageView sendButton;

    public MessageActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        currentGroupId = getIntent().getStringExtra("currentGroupId");

        initComponents();
        initChatData();
        setupRecycleView();
    }

    private void initChatData() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Messages");
        groupsDatabase.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    messageArrayList.clear();
                    currentChatMessages.clear();
                    GenericTypeIndicator<HashMap<String, Message>> indicatorMessages = new GenericTypeIndicator<HashMap<String, Message>>() {};
                    HashMap<String, Message> messageHashMap = snapshot.getValue(indicatorMessages);
                    if (messageHashMap == null) {
                        return;
                    }

                    messageArrayList = new ArrayList<>(messageHashMap.values());
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

        // TODO: Разобраться с компаратор и понять как отсортировать сообщения
        // по полю timestamp
        // currentChatMessages.sort();
    }

    private void initComponents() {
        relative_layout_chat_fragment = findViewById(R.id.relative_layout_chat_fragment);

        EditText editMessage = findViewById(R.id.et_chat);

        ImageView backButton = findViewById(R.id.iv_back_button);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        });

        ImageView sendButton = findViewById(R.id.iv_send_button);
        sendButton.setOnClickListener(view -> {
            String messageText = editMessage.getText().toString();
            if (messageText.isEmpty()) {
                return;
            }

            long timestamp = System.currentTimeMillis();
            DatabaseReference messagesDatabase = FirebaseDatabase.getInstance().getReference("Messages");
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("groupId", currentGroupId);
            hashMap.put("message", messageText);
            hashMap.put("senderId", currentUserId);
            hashMap.put("timestamp", timestamp);

            // TODO: Разобраться как очистить введеный текст в editMessage
            AtomicReference<Boolean> isMessageAdded = new AtomicReference<>(Boolean.FALSE);
            messagesDatabase.push().setValue(hashMap).addOnCompleteListener(task -> isMessageAdded.set(true));
            if (isMessageAdded.get()) {
                editMessage.clearFocus();
                editMessage.getText().clear();
                editMessage.setText("");
            }
        });
    }

    private void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_messages_record);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new MessageFragmentAdapter(currentChatMessages, this);
        recyclerView.setAdapter(adapter);
    }
}
