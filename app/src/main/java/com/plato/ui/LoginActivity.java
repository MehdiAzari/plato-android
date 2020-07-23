package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.plato.MainActivity;
import com.plato.NetworkHandlerThread;
import com.plato.R;

import java.io.IOException;

import com.plato.server.User;

public class LoginActivity extends AppCompatActivity {

    private Button singUpButton;
    private Button continueBtn;
    private NetworkHandlerThread networkHandlerThread = null;
    private EditText username, password;
    private String svMessage;
    private boolean correctUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            networkHandlerThread.start();

            Thread.sleep(50);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        Log.i("Thread", "Start");


        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        continueBtn = findViewById(R.id.continue_button);
        singUpButton = findViewById(R.id.sing_up_instead);

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("svUser", String.valueOf(correctUser));
                if (correctUser) {

                    try {
                        networkHandlerThread.sendUTF("login");
                        networkHandlerThread.getIOHandler().join();
                        networkHandlerThread.sendUTF(username.getText().toString());
                        networkHandlerThread.getIOHandler().join();
                        networkHandlerThread.sendUTF(password.getText().toString());
                        networkHandlerThread.getIOHandler().join();

                        networkHandlerThread.readObject();
                        networkHandlerThread.getIOHandler().join();
                        Thread.sleep(50);

                        Object user = networkHandlerThread.getServerObject();

                        // if user enters wrong password server sends null object
                        if (user == null) {

                            password.setError("Your password is incorrect");

                        } else {
                            Log.i("svUserObj", "got user obj");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            networkHandlerThread.setUser((User)user);
                            startActivity(intent);

                            LoginActivity.this.finish();
                        }


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // after user changes focus from entering username
                if (!hasFocus) {
                    Log.i("sv", "hasFocusBlockReached");
                    String enteredUsername = username.getText().toString();

                    if (enteredUsername.equals("")) {
                        username.setError("Username Can't be empty");
                        return;
                    }

                    try {
                        networkHandlerThread.sendUTF("checkUsername");
                        networkHandlerThread.getIOHandler().join();


                        Log.i("username:", enteredUsername);
                        networkHandlerThread.sendUTF(enteredUsername);
                        networkHandlerThread.getIOHandler().join();

                        networkHandlerThread.readUTF();
                        networkHandlerThread.getIOHandler().join();


                        svMessage = networkHandlerThread.getServerMessage();

                        if (svMessage.equals("username-false")) {
                            username.setError("Username not found");
                            correctUser = false;
                        } else if (svMessage.equals("username-true"))
                            correctUser = true;

                        Log.i("svMessage", svMessage);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

        try {
            NetworkHandlerThread networkHandlerThread = NetworkHandlerThread.getInstance();
            networkHandlerThread.getOos().close();
            networkHandlerThread.getOis().close();
            networkHandlerThread.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onBackPressed();

    }
}
