package com.example.vneurochka.view.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.User;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SearchUserFragment extends DialogFragment {
    private UserFragmentAdapter adapter;
    private DatabaseViewModel databaseViewModel;
    private Collection<User> userList = new ArrayList<>();

    public SearchUserFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initUsers();
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);
        setupRecycleView(view);
        initSearchBar(view);

        return view;
    }

    private void initSearchBar(View view) {
        EditText et_search = view.findViewById(R.id.et_user_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_search.getText().toString().startsWith(" "))
                    et_search.setText("");
            }
        });
    }

    private void initUsers() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Users");
        groupsDatabase.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userList.clear();
                        GenericTypeIndicator<HashMap<String, User>> indicatorUsers = new GenericTypeIndicator<HashMap<String, User>>() {};
                        HashMap<String, User> userHashMap = snapshot.getValue(indicatorUsers);
                        if (userHashMap == null) {
                            return;
                        }

                        userList = userHashMap.values();
                        adapter.setItems(new ArrayList<>(userList));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setupRecycleView(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final RecyclerView recyclerView = view.findViewById(R.id.users_list_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new UserFragmentAdapter(new ArrayList<>(userList));
        recyclerView.setAdapter(adapter);
    }

    private void searchUsers(String searchText) {
        ArrayList<User> requestedUsers = new ArrayList<>();
        if (searchText.isEmpty()) {
            requestedUsers = (ArrayList<User>) userList;
        }
        else {
            for (User user : userList) {
                if (user.getDisplayName().contains(searchText)) {
                    requestedUsers.add(user);
                }
            }
        }

        adapter.setItems(requestedUsers);
        adapter.notifyDataSetChanged();
    }
}
