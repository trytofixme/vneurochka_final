package com.example.vneurochka.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.vneurochka.services.repository.FirebaseInstanceDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class DatabaseViewModel extends ViewModel {
    private final FirebaseInstanceDatabase instance;
    public LiveData<Boolean> successAddUserDb;
    public LiveData<DataSnapshot> fetchUserCurrentData;
    public LiveData<DataSnapshot> fetchUserNames;
    public LiveData<DataSnapshot> fetchSelectedProfileUserData;
    public LiveData<Boolean> successAddImageUrlInDatabase;
    public LiveData<Boolean> successAddUsernameInDatabase;
    public LiveData<Boolean> successAddStatusInDatabase;
    public LiveData<Boolean> successAddIsSeen;
    public LiveData<DataSnapshot> getChaListUserDataSnapshot;
    public LiveData<DataSnapshot> fetchedChat;
    public LiveData<Boolean> successAddChatDb;
    public LiveData<DataSnapshot> fetchSearchUser;

    public DatabaseViewModel()
    {
        instance = new FirebaseInstanceDatabase();
    }

    public void addUserDatabase(String userId, String emailId, String timestamp, String imageUrl)
    {
        successAddUserDb = instance.addUserInDatabase(userId, emailId, timestamp, imageUrl);
    }

    public void fetchingUserDataCurrent()
    {
        fetchUserCurrentData = instance.fetchUserDataCurrent();
    }

    public void fetchUserByNameAll()
    {
        fetchUserNames = instance.fetchAllUserByNames();
    }

    public void fetchSelectedUserProfileData(String userId)
    {
        fetchSelectedProfileUserData = instance.fetchSelectedUserIdData(userId);
    }

    public void addImageUrlInDatabase(String imageUrl, Object mUri)
    {
        successAddImageUrlInDatabase = instance.addImageUrlInDatabase(imageUrl, mUri);
    }

    public void addUsernameInDatabase(String usernameUpdated, Object username)
    {
        successAddUsernameInDatabase = instance.addUsernameInDatabase(usernameUpdated, username);
    }

    public void addStatusInDatabase(String statusUpdated,Object status)
    {
        successAddStatusInDatabase = instance.addStatusInDatabase(statusUpdated, status);
    }

    public void addIsSeenInDatabase(String isSeen,DataSnapshot dataSnapshot){
        successAddIsSeen = instance.addIsSeenInDatabase(isSeen,dataSnapshot);
    }

    public void getChaListUserDataSnapshot(String currentUserId){
        getChaListUserDataSnapshot = instance.getChatList(currentUserId);
    }

    public void fetchChatUser() {
        fetchedChat = instance.fetchChatUser();
    }

    public void addChatDb(String receiverId,String senderId, String message, String timestamp) {
        successAddChatDb = instance.addChatsInDatabase(receiverId, senderId, message, timestamp);
    }

    public void fetchSearchedUser(String searchQuery){
        fetchSearchUser = instance.fetchSearchUser(searchQuery);
    }
}
