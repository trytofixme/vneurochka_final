package com.example.vneurochka.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vneurochka.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class AuthorizationActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button LoginButton, SignUpButton;
    private EditText UserEmail, UserPassword;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        firebaseAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        initializeControls();
        initializeListeners();
    }

    protected void onStart() {
        super.onStart();
        //firebaseAuth.signOut();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            changeUserToHomeActivity();
        }
    }

    private void initializeControls()
    {
        UserEmail = findViewById(R.id.et_email_auth);
        UserPassword = findViewById(R.id.et_password_auth);
        LoginButton = findViewById(R.id.btn_login);
        SignUpButton = findViewById(R.id.btn_login_registration);
    }

    private void initializeListeners()
    {
        LoginButton.setOnClickListener(v -> {
            UserEmail.clearFocus();
            UserPassword.clearFocus();
            dismissKeyboard();

            tryLoginUser();
        });

        SignUpButton.setOnClickListener(v -> {
            changeUserToSignUpActivity();
        });
    }

    private void tryLoginUser()
    {
        String mailText = UserEmail.getText().toString();
        String passwordText = UserPassword.getText().toString();

        if ((mailText.isEmpty() && passwordText.isEmpty())) {
            Toast.makeText(AuthorizationActivity.this, "Почта и пароль должны быть заполнены!", Toast.LENGTH_SHORT).show();
            UserEmail.requestFocus();
        } else if (mailText.isEmpty()) {
            UserEmail.setError("Пожалуйста, введите ваш email.");
            UserEmail.requestFocus();
        } else if (passwordText.isEmpty()) {
            UserPassword.setError("Пожалуйста, введите ваш пароль.");
            UserPassword.requestFocus();
        } else {
            UserEmail.setClickable(false);
            UserPassword.setClickable(false);
        }

        firebaseAuth.signInWithEmailAndPassword(mailText, passwordText).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(deviceToken -> {
                    if (deviceToken.isSuccessful() && deviceToken.getResult() != null) {
                        UserRef.child(currentUserId).child("device_Token").setValue(deviceToken).addOnCompleteListener(userTask -> {
                            if(userTask.isSuccessful()) {
                                changeUserToHomeActivity();
                                Toast.makeText(AuthorizationActivity.this, "Авторизация прошла успешно...", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            else {
                String message = task.getException().toString();
                Toast.makeText(AuthorizationActivity.this, "Ошибка : " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void changeUserToHomeActivity() {
        Intent mainIntent = new Intent(AuthorizationActivity.this, HomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void changeUserToSignUpActivity() {
        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}