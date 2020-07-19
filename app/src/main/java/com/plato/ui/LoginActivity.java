package com.plato.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.plato.NetworkHandlerThread;
import com.plato.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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



//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.i("Socket","connecting to socket...");
//                    Socket socket = new Socket("10.0.2.2", 3535);
//                    Log.i("Socket","connected");
//                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                    Log.i("Streams","HERE");
//                    oos.reset();
//                    oos.writeUTF("HI");
//                    oos.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

//        thread.start();

        try {
            NetworkHandlerThread networkHandlerThread = NetworkHandlerThread.getInstance();
            networkHandlerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
