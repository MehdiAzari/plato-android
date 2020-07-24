package com.plato.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Date;

;

public class ConversationActivity extends AppCompatActivity {

    private NetworkHandlerThread networkHandlerThread;
    private RecyclerView mRecyclerView;
    private MessageListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Conversation conversation;
    private int lastItemPosition = 0;
    private User client;
    private String destUser;
    private TextView appbarTV;
    private EditText chatInput;
    private Button sendBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        appbarTV = findViewById(R.id.appbar_text);
        sendBtn = findViewById(R.id.button_chatbox_send);
        chatInput = findViewById(R.id.edittext_chatbox);
        destUser = getIntent().getStringExtra("destUser");
        String text = "Chating with "+destUser ;
        appbarTV.setText(text);


        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            client = networkHandlerThread.getUser();

        } catch (IOException e) {
            e.printStackTrace();
        }

         conversation = (Conversation) getIntent().getSerializableExtra("c");
        if (conversation == null) {
            Log.i("recTest", "its null :)");
        }
        ArrayList<Message> list = conversation.getMessages();

        if (list == null)
            Log.i("recTest", "list is null :)");

        mRecyclerView = findViewById(R.id.recyclerview_conversation);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessageListAdapter(list);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = chatInput.getText().toString();
                TextMessage msg = new TextMessage(new Date(),client,s);
                conversation.sendMessage(msg);
                mAdapter.notifyItemInserted(mAdapter.getItemCount()+1);
/*
                try {


                    networkHandlerThread.sendUTF("send_message");
                    networkHandlerThread.join();
                    Log.i("chatTest","sending to "+ destUser);
                    networkHandlerThread.sendUTF(destUser);
                    networkHandlerThread.join();
                    networkHandlerThread.sendUTF(s);
                    networkHandlerThread.join();

                    Log.i("chatTest","sent msg to sv");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        mAdapter.notifyDataSetChanged();
    }
}


