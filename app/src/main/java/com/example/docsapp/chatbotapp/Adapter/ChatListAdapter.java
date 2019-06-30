package com.example.docsapp.chatbotapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.docsapp.chatbotapp.Data.Database.MessageObject;
import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.R;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private Context context;
    private ArrayList<MessageObject> chatList;
    private Integer listSizeTrack = 0;

    public interface OnAddNewItem {
        void onAdd(Integer position);
    }

    private OnAddNewItem mCallback;

    public ChatListAdapter(Context context, OnAddNewItem callback) {
        this.context = context;
        this.chatList = new ArrayList<>();
        this.mCallback = callback;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.message_item, viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if (chatList.get(position) != null){
            final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.messageTextView.getLayoutParams();
            if (chatList.get(position).getFlag().equals("sent")){
                holder.messageTextView.setBackground(context.getResources().getDrawable(R.drawable.chat_background_tag));
                params.gravity = GravityCompat.END;
            }else if (chatList.get(position).getFlag().equals("received")){
                holder.messageTextView.setBackground(context.getResources().getDrawable(R.drawable.chat_background_tag2));
                params.gravity = GravityCompat.START;
            }
            holder.messageTextView.setLayoutParams(params);
            holder.messageTextView.setText(chatList.get(position).getMessage());
            if (chatList.size() != listSizeTrack){
                listSizeTrack = chatList.size();
                mCallback.onAdd(chatList.size() - 1);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (chatList == null || chatList.size() == 0)
            return 0;
        else
            return chatList.size();

    }

    public void setMessageObject(MessageObject messageObject){
        chatList.add(messageObject);
        notifyDataSetChanged();
    }

    public void setMessageObjectList(ArrayList<MessageObject> data){
        chatList = data;
        notifyDataSetChanged();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        protected TextView messageTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.chat_message_tv);
        }
    }
}
