package com.plato.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.plato.server.Conversation;
import com.plato.server.User;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.TaskViewHolder> {
    private ArrayList<User> mExampleList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mBody;
        public Switch mSwitch;
        public TaskViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            cardView = itemView.findViewById(R);
            mImageView = itemView.findViewById(R.id.imageView);
            mTitle = itemView.findViewById(R.id.task_example_title);
            mBody = itemView.findViewById(R.id.task_example_body);
            mSwitch = itemView.findViewById(R.id.task_example_switch);


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

    public ChatListAdapter(ArrayList<ConcurrentHashMap<User, Conversation>> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_example, parent, false);
        return new TaskViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskViewHolder holder, int position) {
        final User currentItem = mExampleList.get(position);



        holder.mTitle.setText(currentItem.getTitle());
        holder.mBody.setText(currentItem.getBody());
        holder.mSwitch.setChecked(currentItem.isDone());


        //Check due


    }




    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
