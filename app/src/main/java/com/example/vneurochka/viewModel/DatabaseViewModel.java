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
    public LiveData<Boolean> successAddBioInDatabase;
    public LiveData<Boolean> successAddStatusInDatabase;
    public LiveData<DatabaseReference> getTokenRefDb;

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

    public void addBioInDatabase(String bio, Object bioUpdated)
    {
        successAddBioInDatabase = instance.addBioInDatabase(bio, bioUpdated);
    }

    public void addUsernameInDatabase(String usernameUpdated, Object username)
    {
        successAddUsernameInDatabase = instance.addUsernameInDatabase(usernameUpdated, username);
    }

    public void addStatusInDatabase(String statusUpdated,Object status)
    {
        successAddStatusInDatabase = instance.addStatusInDatabase(statusUpdated, status);
    }

    public void getTokenDatabaseRef()
    {
        getTokenRefDb = instance.getTokenRef();
    }

}
