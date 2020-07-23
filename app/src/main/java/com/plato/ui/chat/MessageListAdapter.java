package com.plato.ui.chat;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Message;
import com.plato.server.TextMessage;
import com.plato.server.User;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter {
    NetworkHandlerThread networkHandlerThread;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_GAME_REPORT = 3;
    private static final int VIEW_TYPE_GAME_GROUP = 4;
    private User client;

    private Context mContext;
    private ArrayList<Message> mMessageList;

    public MessageListAdapter(ArrayList<Message> messageList) {
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        TextMessage message;
        Message m = mMessageList.get(position);

        if(m instanceof TextMessage)
            message = (TextMessage) m;
        else {
            return VIEW_TYPE_GAME_GROUP;
        }
        String sender = message.getSender().getUsername();

        if (sender.equals(client.getUsername())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
            client = networkHandlerThread.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextMessage message = (TextMessage) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(TextMessage message) {
            messageText.setText(message.getContent());

            // Format the stored timestamp into a readable String using method.
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            timeText.setText(dateFormat.format(message.getDate()));

        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
//        vImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
//            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(TextMessage message) {
            messageText.setText(message.getContent());

            // Format the stored timestamp into a readable String using method.
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            timeText.setText(dateFormat.format(message.getDate()));

            nameText.setText(message.getSender().getUsername());

            // Insert the profile image from the URL into the ImageView.

        }
    }
}