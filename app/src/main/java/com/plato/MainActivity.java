package com.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plato.ui.chat.ChatFragment;
import com.plato.ui.friends.FriendsFragment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
//import com.google.android;

public class MainActivity extends AppCompatActivity {

     BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /****************************************************************/

//        NetworkHandlerThread networkHandlerThread = NetworkHandlerThread.getInstance();
//        networkHandlerThread.start();
//
//        ObjectOutputStream oos =  NetworkHandlerThread.getInstance().getOos();
//        try {
//           oos.writeUTF("Hi");
//           oos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



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
