package com.example.vneurochka.view.ui;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import com.example.vneurochka.R;
import com.example.vneurochka.viewModel.DatabaseViewModel;
import com.example.vneurochka.viewModel.SignUpViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    EditText et_password;
    EditText et_repeat_password;
    EditText et_email;
    Button btn_signUp;
    Context context;
    SignUpViewModel signInViewModel;
    DatabaseViewModel databaseViewModel;
    FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        initListeners();
    }

    private void init()
    {
        et_email = findViewById(R.id.e_mail);
        et_password = findViewById(R.id.password); //Rezinka007
        et_repeat_password = findViewById(R.id.rpt_password);
        btn_signUp = findViewById(R.id.btn);
        context = this;
        signInViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(SignUpViewModel.class);
        databaseViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()))
                .get(DatabaseViewModel.class);
    }

    private void initListeners()
    {
        btn_signUp.setOnClickListener(new View.OnClickListener()
        {
            final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

            @Override
            public void onClick(View v)
            {
                et_email.clearFocus();
                et_password.clearFocus();
                et_repeat_password.clearFocus();
                v.startAnimation(buttonClick);

                String passwordText = "Rezinka007";
                String repeatPasswordText = "Rezinka007";
                String emailText = "testmail@test.ru";

                if (passwordText.isEmpty() && emailText.isEmpty() && repeatPasswordText.isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "Fields are empty!", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                } else if (emailText.isEmpty())
                {
                    et_email.setError("Please enter your Email.");
                    et_email.requestFocus();
                } else if (passwordText.isEmpty())
                {
                    et_password.setError("Please set your password.");
                    et_password.requestFocus();
                }
                else if (Character.isDigit(emailText.charAt(0)))
                {
                    et_email.setError("The email cannot start with a digit.");
                    et_email.requestFocus();
                }
                else if (emailText.length() < 4)
                {
                    et_email.setError("Email length from 5 characters.");
                    et_email.requestFocus();
                }
                else if (Character.isDigit(passwordText.charAt(0)))
                {
                    et_password.setError("The password cannot start with a digit.");
                    et_password.requestFocus();
                }
                else if (passwordText.length() < 7)
                {
                    et_password.setError("Password length from 8 characters.");
                    et_password.requestFocus();
                }
                else if (!passwordText.equals(repeatPasswordText))
                {
                    et_password.setError("Passwords don't match!");
                    et_password.requestFocus();
                }
                else
                {
                    et_email.setClickable(false);
                    et_password.setClickable(false);
                    et_repeat_password.setClickable(false);
                    dismissKeyboard();
                    trySignUp();
                }
            }
        });
    }

    public void trySignUp()
    {
        String passwordText = "Rezinka007";
        String emailText = "testmail@test.ru";

        signInViewModel.userSignUp(emailText, passwordText);
        signInViewModel.signInUser.observe(this, task ->
        {
            if (!task.isSuccessful())
            {
                et_email.setClickable(true);
                et_password.setClickable(true);
                et_repeat_password.setClickable(true);

                et_email.setText("");
                et_password.setText("");
                et_email.requestFocus();

                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthUserCollisionException existEmail)
                {
                    Toast.makeText(context, "Email already exists.", Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthWeakPasswordException weakPassword)
                {
                    Toast.makeText(context, "Password length should be more then six characters.", Toast.LENGTH_SHORT).show();
                } catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                {
                    Toast.makeText(context, "Invalid credentials, please try again.", Toast.LENGTH_SHORT).show();
                } catch (Exception e)
                {
                    Toast.makeText(context, "SignUp unsuccessful. Try again.", Toast.LENGTH_SHORT).show();
                }

            } else {
                getUserFirebaseSession();
                addCurrentUserInDatabase(emailText);
                Intent intent = new Intent(SignUpActivity.this, AuthorizationActivity.class);
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

    public void getUserFirebaseSession()
    {
        signInViewModel.getUserFirebaseSession();
        signInViewModel.userFirebaseSession.observe(this, firebaseUser -> currentFirebaseUser = firebaseUser);
    }

    private void addCurrentUserInDatabase(String email)
    {
        String registrationDate = Long.toString(System.currentTimeMillis());
        String imageUrl = "default";
        String userId = currentFirebaseUser.getUid();
        databaseViewModel.addUserDatabase(userId, email, registrationDate, imageUrl);
        databaseViewModel.successAddUserDb.observe(this, success ->
        {
            if (success)
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            else
            {
                Toast.makeText(context, "ERROR WHILE ADDING DATA IN DATABASE.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
