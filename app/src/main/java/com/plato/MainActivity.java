package com.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.plato.ui.chat.ChatFragment;
import com.plato.ui.friends.FriendsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = this.findViewById(R.id.button_nav);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:

                        break;

                    case R.id.nav_play:

                        break;

                    case R.id.nav_chat:
                        ChatFragment fragment = new ChatFragment();
                        openFragment(fragment);
                        break;


                    case R.id.nav_friends:
                        FriendsFragment fragment1 = new FriendsFragment();
                        openFragment(fragment1);
                        break;
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
