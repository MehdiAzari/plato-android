package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.plato.MainActivity;
import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.User;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private EditText username,password, confirmPassword;
    private ImageView avatar;
    private Button btnSingUp;
    private NetworkHandlerThread networkHandlerThread;
    private boolean isAvatarDefault = true;
    private String svMessage;
    private boolean usernameExists = true;
    private boolean isPasswordValid = false;
    private boolean passwordsMatch = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
        }
        username = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        confirmPassword = findViewById(R.id.editText_confirmpassword);
        avatar = findViewById(R.id.appbar_avatar);
        btnSingUp = findViewById(R.id.continue_button);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String input = username.getText().toString();

                    if(input.equals("")) {
                        username.setError("Username Can't be empty");
                        return;
                    }

                    try {
                        networkHandlerThread.sendUTF("checkUsername");
                        networkHandlerThread.getIOHandler().join();

                        Log.i("username:", input);
                        networkHandlerThread.sendUTF(input);
                        networkHandlerThread.getIOHandler().join();

                        networkHandlerThread.readUTF();
                        networkHandlerThread.getIOHandler().join();

                        svMessage = networkHandlerThread.getServerMessage();

                        if(svMessage.equals("username-false")) {

                            usernameExists = false;
                        }
                        else if(svMessage.equals("username-true")) {
                            usernameExists = true;
                            username.setError("Username Exist");
                        }

                        Log.i("svMessage", svMessage);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String input = password.getText().toString();
                if(!hasFocus){
                    if(input.length() <= 5 ) {
                        password.setError("Password must be more than 5 Characters");
                        isPasswordValid = false;
                    }
                    else {
                        isPasswordValid = true;
                    }
                }
            }
        });


        confirmPassword.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String givenPass = password.getText().toString();
                String input = confirmPassword.getText().toString();
                if(!givenPass.equals(input)){
                    confirmPassword.setError("Passwords do not match");
                    passwordsMatch = false;
                }
                else passwordsMatch = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                String givenPass = password.getText().toString();
                String input = confirmPassword.getText().toString();
                if(!givenPass.equals(input)){
                    confirmPassword.setError("Passwords do not match");
                    passwordsMatch = false;
                }
                else passwordsMatch = true;
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("svSignUp","Clicked");
                if(!usernameExists && isPasswordValid && passwordsMatch){
                    Log.i("svSignUp","matches condition");
                    try {
                        networkHandlerThread.sendUTF("register");
                        networkHandlerThread.getIOHandler().join();
                        Log.i("svSignUp","sent register to server");
                        String u = username.getText().toString();
                        String p = password.getText().toString();

                        networkHandlerThread.sendUTF(u);
                        networkHandlerThread.getIOHandler().join();
                        networkHandlerThread.sendUTF(p);
                        networkHandlerThread.getIOHandler().join();

                        networkHandlerThread.readUTF();
                        networkHandlerThread.getIOHandler().join();

                        if(networkHandlerThread.getServerMessage().equals("successful")){
                            networkHandlerThread.readObject();
                            networkHandlerThread.getIOHandler().join();

                            User user = (User) networkHandlerThread.getServerObject();
                            Log.i("svRegisterNewUser",user.toString());

                            Thread.sleep(100);
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            networkHandlerThread.setUser(user);
                            startActivity(intent);

                            SignUpActivity.this.finish();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



    }
}
