package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.plato.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText username,password, confirmPassword;
    private ImageView avatar;
    private boolean isAvatarDefault = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editText_username);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        password = findViewById(R.id.editText_password);
        confirmPassword = findViewById(R.id.editText_confirmpassword);
        avatar = findViewById(R.id.avatar);



    }
}
