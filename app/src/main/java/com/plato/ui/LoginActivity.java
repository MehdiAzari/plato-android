package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.plato.NetworkHandlerThread;
import com.plato.R;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private Button singUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);

        singUpButton = findViewById(R.id.sing_up_instead);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });



        try {
            NetworkHandlerThread networkHandlerThread = NetworkHandlerThread.getInstance();
            networkHandlerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
