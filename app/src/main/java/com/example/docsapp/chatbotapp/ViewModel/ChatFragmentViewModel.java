package com.example.docsapp.chatbotapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.docsapp.chatbotapp.Data.Database.MessageObject;
import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.Repository.AppRepository;

import java.util.ArrayList;

public class ChatFragmentViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private LiveData<MessageObject> messageObject ;
    private MutableLiveData<ArrayList<MessageObject>> userChatList;

    public ChatFragmentViewModel(@NonNull Application application) {
        super(application);
        this.appRepository = AppRepository.getInstance(application);
    }

    public void sendMessageToBot(String userId, String message, String externalId){
       appRepository.sendMessageToBot(userId, message, externalId);
    }

    public LiveData<MessageObject> getMessageObject(){
        messageObject = appRepository.getMutableMessageObject();
        return messageObject;
    }

    public void loadUserChat(String userId, String externalId){
        appRepository.loadUserChatConversation(userId, externalId);
    }

    public MutableLiveData<ArrayList<MessageObject>> getUserChatList() {
        userChatList = appRepository.getMutableMessageList();
        return userChatList;
    }

    public void cleanLiveData(){
        appRepository.clearAllObservers();
    }
}
