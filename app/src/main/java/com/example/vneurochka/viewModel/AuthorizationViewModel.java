package com.example.vneurochka.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.vneurochka.services.repository.FirebaseAuthorizationHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorizationViewModel extends ViewModel {

    private final FirebaseAuthorizationHelper loginInstance;
    public LiveData<Task> logInUser;
    public LiveData<FirebaseUser> firebaseUserLoginStatus;
    public LiveData<FirebaseAuth> firebaseAuthLiveData;


    public AuthorizationViewModel()
    {
        loginInstance = new FirebaseAuthorizationHelper();
    }

    public void userLogIn(String emailLogIn, String pwdLogIn)
    {
        logInUser = loginInstance.loginUser(emailLogIn, pwdLogIn);
    }

    public void getFirebaseUserLogInStatus()
    {
        firebaseUserLoginStatus = loginInstance.getFirebaseUserLoginStatus();
    }

    public void getFirebaseAuth()
    {
        firebaseAuthLiveData = loginInstance.getFirebaseAuth();
    }
}
