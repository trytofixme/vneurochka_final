//package com.example.vneurochka.view.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.vneurochka.R;
//import com.example.vneurochka.model.ChatList;
//import com.example.vneurochka.model.User;
//import com.example.vneurochka.view.adapters.UserFragmentAdapter;
//import com.example.vneurochka.viewModel;
//import com.google.firebase.database.DataSnapshot;
//
//import java.util.ArrayList;
//
//public class ChatFragment extends Fragment {
//    private Context context;
//    private UserFragmentAdapter userAdapter;
//    private ArrayList<User> mUsers;
//    private String currentUserId;
//    private ArrayList<ChatList> userList;
//    private DatabaseViewModel databaseViewModel;
//    private RecyclerView recyclerView_chat_fragment;
//    RelativeLayout relative_layout_chat_fragment;
//
//    public ChatFragment(Context context) {
//        this.context = context;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_chats, container, false);
//        init(view);
//        fetchAllChat();
//
//
//        return view;
//    }
//
//    private void fetchAllChat() {
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
//        databaseViewModel.getChaListUserDataSnapshot(currentUserId);
//        databaseViewModel.getChaListUserDataSnapshot.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
//            @Override
//            public void onChanged(DataSnapshot dataSnapshot) {
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    ChatList chatList = dataSnapshot1.getValue(ChatList.class);
//                    userList.add(chatList);
//                }
//
//                chatUsersList();
//            }
//        });
//    }
//
//    private void chatUsersList() {
//        databaseViewModel.fetchUserByNameAll();
//        databaseViewModel.fetchUserNames.observe(this, new Observer<DataSnapshot>() {
//            @Override
//            public void onChanged(DataSnapshot dataSnapshot) {
//                mUsers.clear();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    User users = dataSnapshot1.getValue(User.class);
//                    for (ChatList chatList : userList) {
//                        assert users != null;
//                        if (users.getId().equals(chatList.getId())) {
//                            if(!mUsers.contains(users)) {
//                                mUsers.add(users);
//                            }
//                        }
//                    }
//                }
//                if(mUsers.isEmpty()){
//                    relative_layout_chat_fragment.setVisibility(View.VISIBLE);
//                }else {
//                    relative_layout_chat_fragment.setVisibility(View.GONE);
//                }
//
//                userAdapter = new UserFragmentAdapter(mUsers, context, true);
//                recyclerView_chat_fragment.setAdapter(userAdapter);
//            }
//        });
//    }
//
//    private void init(View view) {
//        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
//                .getInstance(requireActivity().getApplication()))
//                .get(DatabaseViewModel.class);
//
//        relative_layout_chat_fragment = view.findViewById(R.id.relative_layout_chat_fragment);
//        recyclerView_chat_fragment = view.findViewById(R.id.recycler_view_chat_fragment);
//        recyclerView_chat_fragment.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_chat_fragment.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView_chat_fragment.addItemDecoration(dividerItemDecoration);
//        mUsers = new ArrayList<>();
//        userList = new ArrayList<>();
//
//    }
//}
