package com.example.vneurochka.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vneurochka.R;
import com.example.vneurochka.model.User;
import com.example.vneurochka.view.adapters.UserFragmentAdapter;
import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;


public class GroupsFragment extends Fragment {

    private Context context;
    private DatabaseViewModel databaseViewModel;
    private ArrayList<User> mGroup;
    private String currentUserId;
    private RecyclerView recyclerView;
    private UserFragmentAdapter userFragmentAdapter;
    EditText et_search;

    public GroupsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        init(view);
        fetchingAllGroupName();
        return view;
    }



    private void fetchingAllGroupName() {
        databaseViewModel.fetchingUserDataCurrent();
        databaseViewModel.fetchUserCurrentData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                User users = dataSnapshot.getValue(User.class);
                assert users != null;
                currentUserId = users.getId();
            }
        });

        databaseViewModel.fetchUserByNameAll();
        databaseViewModel.fetchUserNames.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (et_search.getText().toString().equals("")) {
                    mGroup.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        assert user != null;
                        if (!(user.getEmail() == null)
                        ) {
                            if (!currentUserId.equals(user.getId())) {
                                mGroup.add(user);

                            }
                        }
                        userFragmentAdapter = new UserFragmentAdapter(mGroup, context, false);
                        recyclerView.setAdapter(userFragmentAdapter);

                    }

                }
            }
        });
    }

    private void init(View view) {
        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(requireActivity().getApplication()))
                .get(DatabaseViewModel.class);

        recyclerView = view.findViewById(R.id.user_list_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        mGroup = new ArrayList<>();
        et_search = view.findViewById(R.id.et_search);
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

    private void searchUsers(String searchText) {

        if (!(searchText.isEmpty() && searchText.equals(""))) {
            databaseViewModel.fetchSearchedUser(searchText);
            databaseViewModel.fetchSearchUser.observe(this, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(DataSnapshot dataSnapshot) {
                    mGroup.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User users = snapshot.getValue(User.class);
                        assert users != null;
                        if (!users.getId().equals(currentUserId)) {
                            mGroup.add(users);
                        }

                    }
                    userFragmentAdapter = new UserFragmentAdapter(mGroup, context, false);
                    recyclerView.setAdapter(userFragmentAdapter);

                }
            });
        } else {
            fetchingAllGroupName();
        }
    }
}