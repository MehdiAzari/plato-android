package com.plato.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.MainActivity;
import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Conversation;
import com.plato.server.Message;
import com.plato.server.TextMessage;
import com.plato.server.User;

import java.io.IOException;
import java.util.ArrayList;
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

        Thread chatUpdater = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    User client = networkHandlerThread.getUser();
                    try {
                        Thread.sleep(2000); //updates chat every 3 sec
                        networkHandlerThread.sendUTF("update_chat");
                        networkHandlerThread.join();
                        networkHandlerThread.readObject();
                        networkHandlerThread.join();
                        Object o = networkHandlerThread.getServerObject();

                        if(o != null){
                            if(o instanceof  ConcurrentHashMap)
                            client.setConversations((ConcurrentHashMap<User, Conversation>) o);
                            Log.i("svConvUpdate","client's conversations updated");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
//        chatUpdater.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chatlist,container,false);

        mRecyclerView = root.findViewById(R.id.conversationsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        final ArrayList<User> users = new ArrayList<>(conversations.keySet());
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ChatListAdapter(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                lastItemPosition = position;
                User client = networkHandlerThread.getUser();
                User dest = users.get(position);
                ConcurrentHashMap<User,Conversation> c = client.getConversations();
                Conversation conversation = c.get(dest);


//                Log.i("recTest", String.valueOf(list.isEmpty()));


                Intent intent = new Intent(getContext(), ConversationActivity.class);
                lastItemPosition = position;
                intent.putExtra("pos",position);
                intent.putExtra("destUser",dest.getUsername());
                intent.putExtra("c", conversation);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.notifyItemInserted(lastItemPosition);
        mAdapter.notifyDataSetChanged();

    }
}



