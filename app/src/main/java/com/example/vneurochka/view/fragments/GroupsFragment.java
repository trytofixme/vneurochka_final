package com.example.vneurochka.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

    public GroupsFragment()
    {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initControls();
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final RecyclerView recyclerView = view.findViewById(R.id.groups_list_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        List<Group> groupList = databaseViewModel.getUsers();
        adapter = new GroupFragmentAdapter(groupList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initControls() {
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

                    Collection<Group> groupList = groupHashMap.values();
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
//    private void fetchingAllUserNAme() {
//        databaseViewModel.fetchingUserDataCurrent();
//        databaseViewModel.fetchUserCurrentData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
//            @Override
//            public void onChanged(DataSnapshot dataSnapshot) {
//                User users = dataSnapshot.getValue(User.class);
//                assert users != null;
//                currentUserId = users.getId();
//            }
//        });
//
//        databaseViewModel.fetchUserByNameAll();
//        databaseViewModel.fetchUserNames.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
//            @Override
//            public void onChanged(DataSnapshot dataSnapshot) {
//                if (et_search.getText().toString().equals("")) {
//                    mUSer.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        User user = snapshot.getValue(User.class);
//
//                        assert user != null;
//                        if (!(user.getEmail() == null)
//                        ) {
//                            if (!currentUserId.equals(user.getId())) {
//                                mUSer.add(user);
//
//                            }
//                        }
//                        userFragmentAdapter = new UserFragmentAdapter(mUSer, context, false);
//                        recyclerView.setAdapter(userFragmentAdapter);
//
//                    }
//
//                }
//            }
//        });
//    }
//
//    private void init(View view) {
//        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
//                .getInstance(requireActivity().getApplication()))
//                .get(DatabaseViewModel.class);
//
//        recyclerView = view.findViewById(R.id.user_list_recycle_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);
//        mUSer = new ArrayList<>();
//        et_search = view.findViewById(R.id.et_search);
//        et_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    searchGroups(s.toString().toLowerCase());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (et_search.getText().toString().startsWith(" "))
//                    et_search.setText("");
//            }
//        });
//
//    }
//
//    private void searchGroups(String searchText) {
//
//        if (!(searchText.isEmpty() && searchText.equals(""))) {
//            databaseViewModel.fetchSearchedUser(searchText);
//            databaseViewModel.fetchSearchUser.observe(this, new Observer<DataSnapshot>() {
//                @Override
//                public void onChanged(DataSnapshot dataSnapshot) {
//                    mUSer.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        User users = snapshot.getValue(User.class);
//                        assert users != null;
//                        if (!users.getId().equals(currentUserId)) {
//                            mUSer.add(users);
//                        }
//
//                    }
//                    userFragmentAdapter = new UserFragmentAdapter(mUSer, context, false);
//                    recyclerView.setAdapter(userFragmentAdapter);
//
//                }
//            });
//        }else {
//            fetchingAllUserNAme();
//        }
//    }
}
