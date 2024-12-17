package com.example.vneurochka.services.repository;


import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseSignUpHelper {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;
    public MutableLiveData<FirebaseUser> firebaseUsers = new MutableLiveData<>();

    public MutableLiveData<Task> signUpUser(String email, String password) {
        final MutableLiveData<Task> taskSignIn = new MutableLiveData<>();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(value -> {
            firebaseUser = mAuth.getCurrentUser();
            firebaseUsers.setValue(firebaseUser);
            taskSignIn.setValue(value);
        });

        return taskSignIn;
    }
}
