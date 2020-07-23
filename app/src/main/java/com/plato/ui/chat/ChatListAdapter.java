package com.plato.ui.chat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.NetworkHandlerThread;
import com.plato.R;
import com.plato.server.Conversation;
import com.plato.server.Message;
import com.plato.server.TextMessage;
import com.plato.server.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.TaskViewHolder> {
    private ArrayList<User> mExampleList;
    private NetworkHandlerThread networkHandlerThread;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public ImageView mAvatar;
        public TextView mName;
        public TextView mLastMsg;

        public TaskViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            mAvatar = itemView.findViewById(R.id.chatlist_avatar);
            mName = itemView.findViewById(R.id.chatlist_item_name);
            mLastMsg = itemView.findViewById(R.id.chatlist_item_lastmsg);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }

    }

    public ChatListAdapter(ArrayList<User> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_item, parent, false);
        try {
            networkHandlerThread = NetworkHandlerThread.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TaskViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        final User currentItem = mExampleList.get(position);

        Log.i("rec",currentItem.getUsername());
        Log.i("rec", String.valueOf(mExampleList.size()));
        holder.mName.setText(currentItem.getUsername());
        User client = networkHandlerThread.getUser();
        ConcurrentHashMap<User,Conversation> c = client.getConversations();
        Conversation conversation = c.get(currentItem);
        ArrayList<Message> list = conversation.getMessages();
        int indexOfLastMsg = list.size();
        TextMessage textMessage = (TextMessage) list.get(indexOfLastMsg - 1);
        holder.mLastMsg.setText(textMessage.getContent());



        //Check due


    }




    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
