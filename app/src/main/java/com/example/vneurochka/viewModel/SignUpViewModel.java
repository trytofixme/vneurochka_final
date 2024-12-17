package com.example.vneurochka.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.vneurochka.services.repository.FirebaseSignUpHelper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class SignUpViewModel extends ViewModel {

    private final FirebaseSignUpHelper signUpInstance;
    public LiveData<Task> signInUser;
    public  LiveData<FirebaseUser> userFirebaseSession;

    public SignUpViewModel() {
        signUpInstance = new FirebaseSignUpHelper();
    }

    public void userSignUp(String emailSignIn, String passwordSignIn)
    {
        signInUser = signUpInstance.signUpUser(emailSignIn, passwordSignIn);
    }

    public void getUserFirebaseSession()
    {
        userFirebaseSession = signUpInstance.firebaseUsers;
    }
}
