package com.plato.ui.chat;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Message;
import com.plato.server.TextMessage;
import com.plato.server.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    NetworkHandlerThread networkHandlerThread;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
//    private static final int VIEW_TYPE_GAME_REPORT = 3;
//    private static final int VIEW_TYPE_GAME_GROUP = 4;
    private User client;

    private ArrayList<TextMessage> mMessageList;

    public MessageListAdapter(ArrayList<Message> messageList) {
        Log.i("recTest","HEREEEEEEEEEEER");
        ArrayList<TextMessage> textMessages = new ArrayList<>();

        for (Message m : messageList) {
            if (m instanceof TextMessage) {
                textMessages.add((TextMessage) m);
                Log.i("recTest",((TextMessage) m).getContent());
            }
        }

        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = networkHandlerThread.getUser();
        mMessageList = textMessages;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        TextMessage m = mMessageList.get(position);

        String sender = m.getSender().getUsername();

        if (sender.equals(client.getUsername())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextMessage message =  mMessageList.get(position);

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

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText =  itemView.findViewById(R.id.text_message_time);
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

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
//            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
        }

        void bind(TextMessage message) {

            messageText.setText(message.getContent());
            SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
            timeText.setText(dateFormat.format(message.getDate()));
            nameText.setText(message.getSender().getUsername());


        }
    }
}