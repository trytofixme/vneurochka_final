package com.example.vneurochka.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Group;
import com.example.vneurochka.model.User;
import com.example.vneurochka.view.adapters.GroupFragmentAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GroupsFragment extends Fragment {
    private GroupFragmentAdapter adapter;
    private final List<Group> userGroupList;

    public GroupsFragment(List<Group> userGroupList) {
        this.userGroupList = userGroupList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        final RecyclerView recyclerView = view.findViewById(R.id.groups_list_recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);

        // Initializing adapter

        adapter = new GroupFragmentAdapter(userGroupList);

//        FirebaseRecyclerOptions<Group> options = new FirebaseRecyclerOptions.Builder<Group>().setQuery(groupsDatabase.orderByChild("creationDate"), Group.class).build();
//        adapter = new FirebaseRecyclerAdapter<Group, GroupHolder>(options)
//        {
//            @Override
//            protected void onBindViewHolder(@NonNull final GroupHolder holder, int position, @NonNull final Group model)
//            {
//                final String groupId = getRef(position).getKey();
//
//                holder.setHolder(groupId);
//                holder.getView().setOnClickListener(view1 -> {
//                    PopupMenu popup = new PopupMenu(getContext(), view1);
//
//                    popup.getMenu().add(Menu.NONE, 1, 1, "View Profile");
//                    popup.getMenu().add(Menu.NONE, 2, 2, "Send Message");
//
//                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
//                    {
//                        @Override
//                        public boolean onMenuItemClick(MenuItem menuItem)
//                        {
//                            switch(menuItem.getItemId())
//                            {
////                                    case 1:
////                                        Intent userProfileIntent = new Intent(getContext(), ProfileActivity.class);
////                                        userProfileIntent.putExtra("userid", userid);
////                                        startActivity(userProfileIntent);
////                                        return true;
////                                case 2:
////                                    Intent sendMessageIntent = new Intent(getContext(), ChatActivity.class);
////                                    sendMessageIntent.putExtra("groupId", groupId);
////                                    startActivity(sendMessageIntent);
////                                    return true;
//                                default:
//                                    return false;
//                            }
//                        }
//                    });
//                    popup.show();
//                });
//            }

//            @NonNull
//            @Override
//            public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
//            {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_view, parent, false);
//
//                return new GroupHolder(view);
//            }

//            @Override
//            public void onDataChanged()
//            {
//                super.onDataChanged();
//
//                TextView text = view.findViewById(R.id.f_friends_text);
//
//                if(adapter.getItemCount() == 0)
//                {
//                    text.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    text.setVisibility(View.GONE);
//                }
//
//                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
//            }
//        };
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initControls() {

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
