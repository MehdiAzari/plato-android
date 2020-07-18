package com.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plato.ui.chat.ChatFragment;
import com.plato.ui.friends.FriendsFragment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /****************************************************************/

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("felan","HERE");
                    Socket socket = new Socket("172.20.10.2",4000);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Log.i("Object input stream", ois.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();



        /****************************************************************/

        bottomNavigation = this.findViewById(R.id.button_nav);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        return true;


                    case R.id.nav_play:

                        return true;

                    case R.id.nav_chat:
                        ChatFragment fragment = new ChatFragment();
                        openFragment(fragment);
                        return true;


                    case R.id.nav_friends:
                        FriendsFragment fragment1 = new FriendsFragment();
                        openFragment(fragment1);
                        return true;
                }

                return false;
            }
        });
    }

    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}
