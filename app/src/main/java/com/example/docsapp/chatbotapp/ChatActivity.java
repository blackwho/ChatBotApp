package com.example.docsapp.chatbotapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.docsapp.chatbotapp.Fragment.ChatFragment;

public class ChatActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        fragmentManager = getSupportFragmentManager();
        startChatFragment();
    }

    private void startChatFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChatFragment chatFragment = new ChatFragment();
        transaction.replace(R.id.chat_frag_container, chatFragment, "chatFrag")
                .commit();
    }
}
