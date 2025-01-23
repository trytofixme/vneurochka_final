package com.example.vneurochka.view.ui;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import com.example.vneurochka.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;

    private EditText UserEmail, UserLogin, UserPassword, UserRepeatPassword;
    private TextView AlreadyHaveAccountLink;
    Button SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");

        initControls();
        initListeners();
    }

    private void initControls()
    {
        UserEmail = findViewById(R.id.et_email_registration);
        UserLogin = findViewById(R.id.et_login_registration);
        UserPassword = findViewById(R.id.et_password_registration);
        UserRepeatPassword = findViewById(R.id.et_rpt_password_registration);
        SignUpButton = findViewById(R.id.btn_registration);
        AlreadyHaveAccountLink = findViewById(R.id.tv_already_have_account_link);
    }

    private void initListeners()
    {
        AlreadyHaveAccountLink.setOnClickListener(view -> changeUserToAuthorizationActivity());

        SignUpButton.setOnClickListener(v -> {
            UserEmail.clearFocus();
            UserLogin.clearFocus();
            UserPassword.clearFocus();
            UserRepeatPassword.clearFocus();

            trySignUp();
        });
    }

    public void trySignUp() {
        String emailText = UserEmail.getText().toString();
        String loginText = UserLogin.getText().toString();
        String passwordText = UserPassword.getText().toString();
        String repeatPasswordText = UserRepeatPassword.getText().toString();

        if (emailText.isEmpty() && loginText.isEmpty() && passwordText.isEmpty() && repeatPasswordText.isEmpty()) {
            Toast.makeText(SignUpActivity.this, getText(R.string.no_fills), Toast.LENGTH_SHORT).show();
            UserEmail.requestFocus();
        } else if (emailText.isEmpty()) {
            UserEmail.setError(getText(R.string.no_email_reg));
            UserEmail.requestFocus();
        } else if (passwordText.isEmpty()) {
            UserPassword.setError(getText(R.string.no_ps_reg));
            UserPassword.requestFocus();
        }
        // Валидация почты
        else if (Character.isDigit(emailText.charAt(0))) {
            UserEmail.setError(getText(R.string.dig_email));
            UserEmail.requestFocus();
        } else if (emailText.length() < 4) {
            UserEmail.setError(getText(R.string.len_email));
            UserEmail.requestFocus();
        }
        // Валидация пароля
        else if (Character.isDigit(passwordText.charAt(0))) {
            UserPassword.setError(getText(R.string.dig_ps));
            UserPassword.requestFocus();
        } else if (passwordText.length() < 7) {
            UserPassword.setError(getText(R.string.len_ps));
            UserPassword.requestFocus();
        } else if (!passwordText.equals(repeatPasswordText)) {
            UserPassword.setError(getText(R.string.ps_no_mch));
            UserPassword.requestFocus();
        } else {
            UserEmail.setClickable(false);
            UserLogin.setClickable(false);
            UserPassword.setClickable(false);
            UserRepeatPassword.setClickable(false);
            dismissKeyboard();

            mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String currentUserId = mAuth.getCurrentUser().getUid();

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", UserLogin.getText().toString());
                    map.put("status", "online");
                    map.put("imageUrl", "default");

                    UsersRef.child(currentUserId).setValue(map).addOnCompleteListener(onCompleteTask -> {
                        if (onCompleteTask.isSuccessful()) {
                            changeUserToHomeActivity();
                            Toast.makeText(SignUpActivity.this, getText(R.string.acc_done), Toast.LENGTH_SHORT).show();
                        } else {
                            String message = onCompleteTask.getException().toString();
                            Toast.makeText(SignUpActivity.this, getText(R.string.error) + message, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    String message = task.getException().toString();
                    Toast.makeText(SignUpActivity.this, getText(R.string.error) + message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void dismissKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    private void changeUserToHomeActivity() {
        Intent mainIntent = new Intent(SignUpActivity.this, HomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void changeUserToAuthorizationActivity() {
        Intent intent = new Intent(getBaseContext(), AuthorizationActivity.class);
        startActivity(intent);
    }
}
