package com.plato.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Conversation;
import com.plato.server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ChatFragment extends Fragment {
    private NetworkHandlerThread networkHandlerThread;
    private ConcurrentHashMap<User, Conversation> conversations;
    private RecyclerView mRecyclerView;
    private ChatListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int lastItemPosition = 0;
    private User user;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            user = networkHandlerThread.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        conversations = user.getConversations();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_conversation,container,false);

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        ArrayList<User> users = new ArrayList<>(conversations.keySet());
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ChatListAdapter(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new TaskListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(MainActivity.this,ViewTaskActivity.class);
//                lastItemPosition = position;
//                intent.putExtra("pos",position);
//                startActivity(intent);
//            }
//        });


        return root;
    }



}



