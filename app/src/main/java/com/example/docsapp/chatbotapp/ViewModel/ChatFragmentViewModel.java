package com.example.docsapp.chatbotapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.Repository.AppRepository;

public class ChatFragmentViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private LiveData<MessageModel> messageModel ;

    public ChatFragmentViewModel(@NonNull Application application) {
        super(application);
        this.appRepository = AppRepository.getInstance(application);
    }

    public void sendMessageToBot(String message){
       appRepository.sendMessageToBot(message);
    }

    public LiveData<MessageModel> getMessageModel(){
        messageModel = appRepository.getMutableMessageModel();
        return messageModel;
    }
}
