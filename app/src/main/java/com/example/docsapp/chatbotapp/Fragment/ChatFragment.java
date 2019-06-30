package com.example.docsapp.chatbotapp.Fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.docsapp.chatbotapp.Adapter.ChatListAdapter;
import com.example.docsapp.chatbotapp.Data.Database.MessageObject;
import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.R;
import com.example.docsapp.chatbotapp.ViewModel.ChatFragmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements ChatListAdapter.OnAddNewItem {

    private RecyclerView mRecyclerView;
    private ChatListAdapter mAdapter;
    private ChatFragmentViewModel mViewModel;
    private EditText chatEditText;
    private ImageButton sendImageButton;
    private String externalId;
    private String userId;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
        mViewModel.cleanLiveData();
        mViewModel.getMessageObject().removeObservers(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            externalId = getArguments().getString("externalId");
            userId = getArguments().getString("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this).get(ChatFragmentViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new ChatListAdapter(getContext(), this);
        mRecyclerView = view.findViewById(R.id.chat_list_rv);
        chatEditText = view.findViewById(R.id.chat_text_ev);
        sendImageButton = view.findViewById(R.id.send_button_iv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        viewModelAttach();
        attachListeners();
    }

    private void attachListeners(){
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageString = chatEditText.getText().toString();
                if (!messageString.isEmpty()){
                    mViewModel.sendMessageToBot(userId, messageString, externalId);
                    MessageObject messageObject = new MessageObject(null, messageString, "sent");
                    mAdapter.setMessageObject(messageObject);
                    chatEditText.getText().clear();
                }else {
                    Toast.makeText(getContext(), "Please enter message", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void viewModelAttach(){
        mViewModel.loadUserChat(userId, externalId);

        mViewModel.getUserChatList().observe(getViewLifecycleOwner(), messageObjectList -> {
            Log.v("TAG", "messageObjectList" + messageObjectList);
            try {
                if (messageObjectList != null || !messageObjectList.isEmpty()){
                    mAdapter.setMessageObjectList(messageObjectList);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });

        mViewModel.getMessageObject().observe(getViewLifecycleOwner(), messageModel -> {
            Log.v("TAG", "messageModel" + messageModel);
            if (messageModel != null){
                mAdapter.setMessageObject(messageModel);
            }
        });

    }

    @Override
    public void onAdd(Integer position) {
        mRecyclerView.smoothScrollToPosition(position);
    }
}
