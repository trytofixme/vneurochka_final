package com.example.vneurochka.viewModel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.vneurochka.model.Group;
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

public class DatabaseViewModel extends ViewModel {
    private final List<Group> userGroupList = new ArrayList<>();

    public DatabaseViewModel() {
    }

    public void fetchUserGroups(String currentUserId) {
        DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Groups");
        //groupsDatabase.keepSynced(true);
        groupsDatabase.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        GenericTypeIndicator<HashMap<String, Group>> indicatorGroups = new GenericTypeIndicator<HashMap<String, Group>>() {};
                        Collection<Group> groupList = snapshot.getValue(indicatorGroups).values();
                        if (groupList == null) {
                            return;
                        }

                        for (Group group: groupList) {
                            List<String> userList = group.getUsers();

                            if (userList != null && userList.contains(currentUserId)) {
                                userGroupList.add(group);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public List<Group> getUsers() {
        return this.userGroupList;
    }

}