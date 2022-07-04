package com.assignment.natnaelalemayehu_greenflag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    //Fields
    private SharedPreferences sharedPreferences;
    private final String FORM_STATE = "form_state";
    private final String SHARED_ID = "sharedId";
    private  final String PREFERENCE_ID = "sharedEmail";
    private Map<Integer, String> emailsList = new HashMap<>();
    private String[] formStateArray = new String[3];
    private ImageButton btn_back;
    private EditText input_email, input_pass1, input_pass2;
    private TextView txt_email_err, txt_pass_err, txt_pass_err_criteria;
    private ImageButton next;
    boolean isValid = false;
    private String email;
    private String password1;
    private String password2;
    private String shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            shared = savedInstanceState.getString(SHARED_ID);
        }

        // Initialize views
        initViews();

        // Initialize views
        backToHome();

        // Validate email
        isEmailValid(input_email);

        // Validate password
        isPasswordValid(input_pass1, input_pass2);

        // Retrieve data from from saved instance state
        getInstanceStateData(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(FORM_STATE, formStateArray) ;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        formStateArray = savedInstanceState.getStringArray(FORM_STATE);
    }

    /**
     * Initialize views
     */
    private void initViews() {
        btn_back = findViewById(R.id.btn_back);
        input_email = findViewById(R.id.input_email);
        input_pass1 = findViewById(R.id.input_pass1);
        input_pass2 = findViewById(R.id.input_pass2);
        txt_email_err = findViewById(R.id.txt_email_err);
        txt_pass_err_criteria = findViewById(R.id.txt_pass_err_criteria);
        txt_pass_err = findViewById(R.id.txt_pass_err);
        next = findViewById(R.id.btn_account);
    }

    /**
     * Back to to home
     */
    private void backToHome() {
        btn_back.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    /**
     * Validate email
     * @param input_email
     */
    private void isEmailValid(EditText input_email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{3,6}$";

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email = input_email.getText().toString();
                if(!isValidInput(email, regex)) {
                    if (email.equals(getEmail())) {
                        txt_email_err.setText("An account already exists for this email address.");
                    } else {
                        txt_email_err.setText("Your email isn't valid.");
                    }

                    input_email.setBackgroundResource(R.drawable.error_input_borber);
                    txt_email_err.setBackgroundResource(R.drawable.error_border);
                    txt_email_err.setVisibility(View.VISIBLE);
                    isValid = false;

                } else {
                    txt_email_err.setText("");
                    txt_email_err.setVisibility(View.GONE);
                    input_email.setBackgroundResource(R.drawable.green_border);
                    input_email.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.tick), null);
                }
            }
        });
    }

    /**
     * Validate password
     * @param input_pass1
     * @param input_pass2
     */
    private void isPasswordValid(EditText input_pass1, EditText input_pass2) {
        // Regex to check valid password.
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])[^\\s]{8,20}$";

        input_pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password1 = input_pass1.getText().toString();
                if(!isValidInput(password1, regex)) {
                    input_pass1.setBackgroundResource(R.drawable.error_input_borber);
                    txt_pass_err_criteria.setBackgroundResource(R.drawable.error_border);
                    txt_pass_err_criteria.setText("You have entered invalid password.");
                    txt_pass_err_criteria.setVisibility(View.VISIBLE);
                    isValid = false;
                } else {
                    txt_pass_err_criteria.setText("");
                    txt_pass_err_criteria.setVisibility(View.GONE);
                    input_pass1.setBackgroundResource(R.drawable.green_border);
                    input_pass1.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.tick), null);
                }
            }
        });

        input_pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password2 = input_pass2.getText().toString();
                if (password2.equals(password1)) {
                    input_pass2.setBackgroundResource(R.drawable.green_border);
                    input_pass2.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.tick), null);
                    txt_pass_err.setText("");
                    txt_pass_err.setVisibility(View.GONE);
                    isValid = true;

                    /* Check button is clickable */
                    isButtonClickable();
                } else {
                    input_pass2.setBackgroundResource(R.drawable.error_input_borber);
                    txt_pass_err.setBackgroundResource(R.drawable.error_border);
                    txt_pass_err.setText("Your passwords don't match");
                    txt_pass_err.setVisibility(View.VISIBLE);
                    isValid = false;
                }
            }
        });
    }

    /**
     * Check button is clickable
     */
    private void isButtonClickable() {
        if (isValid == true) {
            /* Enable next button */
            next.setColorFilter(Color.argb(100, 0,255, 0));
            next.setEnabled(true);
            next.setOnClickListener(view -> {
                // Reset shared references
                resetSharedReferences();

                // Save email to shared preference
                saveEmail();
                emailsList.put(1, email);

                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                //Toast.makeText(this,  "Your form is valid", Toast.LENGTH_LONG).show();

                /* Clear form inputs */
                clearForm();
            });
        }
    }

    /**
     * Validate inputs against regex pattern
     * @param input
     * @param regex
     * @return
     */
    private boolean isValidInput(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Clear form inputs
     */
    private void clearForm() {
        input_email.getText().clear();
        input_pass1.getText().clear();
        input_pass2.getText().clear();
        input_email.setBackgroundResource(R.drawable.green_border);
        input_pass1.setBackgroundResource(R.drawable.green_border);
        input_pass2.setBackgroundResource(R.drawable.green_border);
        txt_email_err.setBackgroundResource(R.drawable.green_border);
        txt_pass_err_criteria.setBackgroundResource(R.drawable.green_border);
        txt_pass_err.setBackgroundResource(R.drawable.green_border);
        txt_email_err.setVisibility(View.GONE);
        txt_pass_err_criteria.setVisibility(View.GONE);
        txt_pass_err.setVisibility(View.GONE);
    }

    /**
     * Reset shared references
     */
    private void resetSharedReferences() {
        // Clear preferences
        SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    /**
     * Retrieve data from from savedInstanceState
     * @param savedInstanceState
     */
    private void getInstanceStateData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            formStateArray = savedInstanceState.getStringArray(FORM_STATE);
            input_email.setText(formStateArray[0]);
            input_pass1.setText(formStateArray[1]);
            input_pass2.setText(formStateArray[2]);
        }
    }

    /**
     * Save email to shared preference
     */
    private void saveEmail() {
        // Initialize shared references
        sharedPreferences = getSharedPreferences(PREFERENCE_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.commit();
    }

    /**
     * Get email from shared preference
     */
    public String getEmail() {
        sharedPreferences = getSharedPreferences(PREFERENCE_ID, MODE_PRIVATE);
        return sharedPreferences.getString("Email", "");
    }


}