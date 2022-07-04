package com.assignment.natnaelalemayehu_greenflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_account;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn_account = findViewById(R.id.btn_account);

        /* Open login page */
        createAccount();
    }

    private void createAccount() {
        btn_account.setOnClickListener(view -> {
            startActivity(new Intent(this, Login.class));
        });
    }
}