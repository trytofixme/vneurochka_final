package com.example.vneurochka.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.vneurochka.services.repository.FirebaseAuthorizationInstance;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationViewModel extends ViewModel {
    private final FirebaseAuthorizationInstance authorizationInstance;
    public LiveData<Task> authorizationResult;
    public LiveData<FirebaseUser> firebaseUser;
    public LiveData<FirebaseAuth> firebaseAuth;

    public AuthorizationViewModel()
    {
        authorizationInstance = new FirebaseAuthorizationInstance();
    }

    public void authorizeUser(String email, String password)
    {
        authorizationResult = authorizationInstance.authorizeUser(email, password);
    }

    public void getFirebaseUser()
    {
        firebaseUser = authorizationInstance.getFirebaseUser();
    }

    public void getFirebaseAuth()
    {
        firebaseAuth = authorizationInstance.getFirebaseAuth();
    }
}
