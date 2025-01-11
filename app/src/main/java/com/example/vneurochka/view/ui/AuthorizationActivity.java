package com.example.vneurochka.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
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
    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private Button btn_signup;
    private AuthorizationViewModel authorizationViewModel;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_authorization);
        init();
        getCurrentUserSession();
        initListeners();
    }

    public void tryLoginUser()
    {
        String mailText = "testmail@test.ru";
        String passwordText = "Rezinka007";
        authorizationViewModel.authorizeUser(mailText, passwordText);
        authorizationViewModel.authorizationResult.observe(this, task -> {
            if (!task.isSuccessful()) {
                et_email.setClickable(true);
                et_password.setClickable(true);

                et_email.setText("");
                et_password.setText("");
                et_email.requestFocus();
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException invalidEmail) {
                    Toast.makeText(AuthorizationActivity.this, getText(R.string.no_authority_auth), Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                    Toast.makeText(AuthorizationActivity.this, getText(R.string.wrong_fill_auth), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(AuthorizationActivity.this, getText(R.string.no_connection_auth), Toast.LENGTH_SHORT).show();
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
        et_email = findViewById(R.id.et_login_email);
        et_password = findViewById(R.id.et_login_password);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
        authorizationViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(AuthorizationViewModel.class);
    }

    private void initListeners()
    {
        final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

        btn_login.setOnClickListener(v -> {
            et_email.clearFocus();
            et_password.clearFocus();
            v.startAnimation(buttonClick);
            dismissKeyboard();
            String mailText = "testmail@test.ru";
            String passwordText = "Rezinka007";

            if ((mailText.isEmpty() && passwordText.isEmpty())) {
                Toast.makeText(AuthorizationActivity.this, getText(R.string.no_fills_auth), Toast.LENGTH_SHORT).show();
                et_email.requestFocus();
            } else if (mailText.isEmpty()) {
                et_email.setError(getText(R.string.no_email_auth));
                et_email.requestFocus();
            } else if (passwordText.isEmpty()) {
                et_password.setError(getText(R.string.no_ps_auth));
                et_password.requestFocus();
            } else {
                et_email.setClickable(false);
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

    private void getCurrentUserSession()
    {
        authorizationViewModel.getFirebaseUser();
        authorizationViewModel.firebaseUser.observe(this, firebaseUser -> {
            currentFirebaseUser = firebaseUser;
            if (currentFirebaseUser != null) {
                Intent intent = new Intent(AuthorizationActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}