package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.plato.NetworkHandlerThread;
import com.plato.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {

    private Button singUpButton;
    private NetworkHandlerThread networkHandlerThread = null;
    private EditText username , password;
    private String svMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            networkHandlerThread.start();


            Thread.sleep(100);
            Log.i("Thread","Start");

        } catch (Exception e) {

            e.printStackTrace();
        }

        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        singUpButton = findViewById(R.id.sing_up_instead);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });



        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // after user changes focus from entering username
                if(!hasFocus){
                    networkHandlerThread.sendString("checkUsername");
                    String enteredUsername = username.getText().toString();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("username:",enteredUsername);
                    networkHandlerThread.sendString(enteredUsername);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    svMessage = networkHandlerThread.getServerMessage();
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("svMessage",svMessage);
                }



            }
        });







    }
}
