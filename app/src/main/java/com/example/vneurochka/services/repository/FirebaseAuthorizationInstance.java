package com.example.vneurochka.services.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthorizationInstance {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    public MutableLiveData<FirebaseAuth> getFirebaseAuth() {
        final MutableLiveData<FirebaseAuth> firebaseAuth = new MutableLiveData<>();
        firebaseAuth.setValue(this.firebaseAuth);
        return firebaseAuth;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUser() {
        final MutableLiveData<FirebaseUser> firebaseUserLoginStatus = new MutableLiveData<>();
        firebaseUserLoginStatus.setValue(firebaseUser);
        return firebaseUserLoginStatus;
    }

    public MutableLiveData<Task> authorizeUser(String email, String password) {
        final MutableLiveData<Task> authorizationTask = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(authorizationTask::setValue);

        return authorizationTask;
    }
}
