package com.example.vneurochka.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Group;
import com.example.vneurochka.view.adapters.GroupFragmentAdapter;
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

public class GroupsFragment extends Fragment {
    private GroupFragmentAdapter adapter;
    private DatabaseViewModel databaseViewModel;
    private List<Group> userGroupList = new ArrayList<>();
    private Collection<Group> groupList = new ArrayList<>();

    public GroupsFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initUserGroups();
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        setupRecycleView(view);
        initSearchBar(view);

        return view;
    }

    private void initSearchBar(View view) {
        EditText et_search = view.findViewById(R.id.et_group_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchGroups(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_search.getText().toString().startsWith(" "))
                    et_search.setText("");
            }
        });
    }

    private void initUserGroups() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Groups");
        //groupsDatabase.keepSynced(true);
        groupsDatabase.addValueEventListener(
            new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<HashMap<String, Group>> indicatorGroups = new GenericTypeIndicator<HashMap<String, Group>>() {};
                    HashMap<String, Group> groupHashMap = snapshot.getValue(indicatorGroups);
                    if (groupHashMap == null) {
                        return;
                    }

                    groupList = groupHashMap.values();
                    for (Group group: groupList) {
                        List<String> userList = group.getUserIds();

                        if (userList != null && userList.contains(currentUserId)) {
                            userGroupList.add(group);
                        }
                    }

                    adapter.setItems(userGroupList);
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

        final RecyclerView recyclerView = view.findViewById(R.id.groups_list_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        adapter = new GroupFragmentAdapter(userGroupList);
        recyclerView.setAdapter(adapter);
    }

    private void searchGroups(String searchText) {
        ArrayList<Group> requestedGroups = new ArrayList<>();
        for (Group group : groupList) {
            if (group.getName().contains(searchText)) {
                requestedGroups.add(group);
            }
        }

        adapter.setItems(requestedGroups);
        adapter.notifyDataSetChanged();
    }
}
