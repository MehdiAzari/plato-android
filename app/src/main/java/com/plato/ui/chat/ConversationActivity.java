package com.plato.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Conversation;
import com.plato.server.Message;
import com.plato.server.TextMessage;
import com.plato.server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

;

public class ConversationActivity extends AppCompatActivity {

    private NetworkHandlerThread networkHandlerThread;
    private RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int lastItemPosition = 0;
    private User Client;
    private User destUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Log.i("recTest","FUCKU");

        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            Client = networkHandlerThread.getUser();

        } catch (IOException e) { e.printStackTrace();}

            Log.i("recTest","FUCKU2");
//            String username = getIntent().getStringExtra("destUser");

            destUser = (User) getIntent().getSerializableExtra("destUser");


            mRecyclerView = findViewById(R.id.recyclerview_message_list);
            mRecyclerView.setHasFixedSize(true);
            ArrayList<Message> messages = Client.getConversations().get(destUser).getMessages();
            User client = networkHandlerThread.getUser();
            ConcurrentHashMap<User, Conversation> c = client.getConversations();
//            Iterator itr = c.keySet().iterator();

//            while (itr.hasNext()){
//                User next = (User) itr.next();
//                if(next.getUsername().equals(username))
//                    destUser = next;
//            }

            Conversation conversation = c.get(destUser);
            ArrayList<Message> list = conversation.getMessages();
            Log.i("recTest",((TextMessage)list.get(0)).getContent());
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mAdapter = new MessageListAdapter(list);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);


        }


    @Override
    public void onResume() {
        super.onResume();

        mAdapter.notifyItemChanged(lastItemPosition);
        mAdapter.notifyDataSetChanged();
    }
}


