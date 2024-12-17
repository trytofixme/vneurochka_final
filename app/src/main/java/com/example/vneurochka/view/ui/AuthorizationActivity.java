package com.example.vneurochka.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.vneurochka.R;
import com.example.vneurochka.viewModel.AuthorizationViewModel;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AuthorizationActivity extends AppCompatActivity {
    EditText et_mail;
    EditText et_password;
    Button btn_login;
    Button btn_signup;
    AuthorizationViewModel authorizationViewModel;
    FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_activity);
        init();
        getUserSession();
        initListeners();
    }

    public void tryLoginUser()
    {
        String mailText = "testmail@test.ru";
        String passwordText = "Rezinka007";
        authorizationViewModel.userLogIn(mailText, passwordText);
        authorizationViewModel.logInUser.observe(this, task -> {
            if (!task.isSuccessful()) {
                et_mail.setClickable(true);
                et_password.setClickable(true);

                et_mail.setText("");
                et_password.setText("");
                et_mail.requestFocus();
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException invalidEmail) {
                    Toast.makeText(AuthorizationActivity.this, "Invalid credentials, please try again.", Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                    Toast.makeText(AuthorizationActivity.this, "Wrong password or username , please try again.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AuthorizationActivity.this, "Check Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            } else {

                Intent intent = new Intent(AuthorizationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void init()
    {
        et_mail = findViewById(R.id.login);
        et_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn);
        btn_signup = findViewById(R.id.registration);
        authorizationViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(AuthorizationViewModel.class);
    }

    private void initListeners()
    {
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        btn_login.setOnClickListener(v -> {
            et_mail.clearFocus();
            et_password.clearFocus();
            v.startAnimation(buttonClick);
            dismissKeyboard();
            String mailText = "testmail@test.ru";
            String passwordText = "Rezinka007";

            if ((mailText.isEmpty() && passwordText.isEmpty())) {
                Toast.makeText(AuthorizationActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                et_mail.requestFocus();
            } else if (mailText.isEmpty()) {
                et_mail.setError("Please enter your Email Id.");
                et_mail.requestFocus();
            } else if (passwordText.isEmpty()) {
                et_password.setError("Please enter your password.");
                et_password.requestFocus();
            } else {
                et_mail.setClickable(false);
                et_password.setClickable(false);
                tryLoginUser();
            }
        });

        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getUserSession()
    {
        authorizationViewModel.getFirebaseUserLogInStatus();
        authorizationViewModel.firebaseUserLoginStatus.observe(this, firebaseUser -> {
            currentFirebaseUser = firebaseUser;
            if (currentFirebaseUser != null) {
                Intent intent = new Intent(AuthorizationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}