package com.example.docsapp.chatbotapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.docsapp.chatbotapp.Data.Database.AppDatabase;
import com.example.docsapp.chatbotapp.Data.Database.ChatBotDao;
import com.example.docsapp.chatbotapp.Data.Database.MessageObject;
import com.example.docsapp.chatbotapp.Data.Database.UserEntity;
import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.Network.GsonRequest;
import com.example.docsapp.chatbotapp.Network.VolleySingleton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();
    private static AppRepository mInstance;
    private RequestQueue mRequestQueue;
    private Context context;
    private static MutableLiveData<MessageObject> mutableMessageObject;
    private MutableLiveData<ArrayList<MessageObject>> mutableMessageList;
    private ChatBotDao mChatBotDao;

    private AppRepository(Application application) {
        mRequestQueue = VolleySingleton.getInstance(application.getApplicationContext()).getRequestQueue();
        this.context = application.getApplicationContext();
        mutableMessageObject = new MutableLiveData<>();
        mutableMessageList = new MutableLiveData<>();
        AppDatabase db = AppDatabase.getDatabase(application);
        mChatBotDao = db.chatBotDao();
    }



    public static AppRepository getInstance(Application application){
        if(mInstance == null){
            mInstance = new AppRepository(application);
        }
        return mInstance;
    }

    public MutableLiveData<MessageObject> getMutableMessageObject(){
        if (mutableMessageObject == null){
            mutableMessageObject = new MutableLiveData<>();
        }
        return mutableMessageObject;
    }

    public void sendMessageToBot(String userId, String message, String externalId){
        String URL = urlBuilder(message, externalId);
        //TODO: update sent message in database
        updateMessage(userId, message, "sent");
        GsonRequest mJsonObjectRequest = new GsonRequest<>(Request.Method.GET,
                URL,
                JsonObject.class,
                null,
                new Response.Listener<JsonObject>() {
                    @Override
                    public void onResponse(JsonObject response) {
                        Log.v("TAG", "response" + response.toString());
                        try {
                            JSONObject json = new JSONObject(response.toString());
                            JSONObject message = json.getJSONObject("message");
                            String messageBody = message.getString("message");
                            Date date = new Date();
                            Long timeStamp = date.getTime();
                            MessageObject messageObject = new MessageObject(timeStamp, messageBody, "received");
                            mutableMessageObject.postValue(messageObject);
                            updateMessage(userId, messageBody, "received");
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("TAG", "Error:" + error);
            }
        });
        mRequestQueue.add(mJsonObjectRequest);
    }

    private String urlBuilder(String message, String externalId){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.personalityforge.com")
                .appendPath("api")
                .appendPath("chat")
                .appendQueryParameter("apiKey", "6nt5d1nJHkqbkphe")
                .appendQueryParameter("message", message)
                .appendQueryParameter("chatBotID", String.valueOf(63906))
                .appendQueryParameter("externalID", externalId);
        return builder.build().toString();

    }


    public MutableLiveData<ArrayList<MessageObject>> getMutableMessageList(){
        if (mutableMessageList == null){
            mutableMessageList = new MutableLiveData<>();
        }
        return mutableMessageList;
    }

    public void loadUserChatConversation(String userId, String externalId){
        GetConversation getConversation = new GetConversation(mChatBotDao, mutableMessageList);
        getConversation.execute(userId, externalId);
    }

    public void updateMessage(String userId, String message, String flag){
        Date date = new Date();
        Long timeStamp = date.getTime();
        UpdateMessage updateMessage = new UpdateMessage(mChatBotDao, timeStamp);
        updateMessage.execute(userId, message, flag);
    }

    public void clearAllObservers(){
        mutableMessageObject.setValue(null);
        mutableMessageList.setValue(null);
    }


    public static class UpdateMessage extends AsyncTask<String, Void, Void> {

        private ChatBotDao mDao;
        private Long timeStamp;

        UpdateMessage(ChatBotDao dao, Long mTimeStamp) {
            mDao = dao;
            timeStamp = mTimeStamp;
        }

        @Override
        protected Void doInBackground(String... strings) {
            String userId = strings[0];
            String message = strings[1];
            String flag = strings[2];
            ArrayList<MessageObject> messageObjects = mDao.getUserChatConvo(userId).messageList;
            if (messageObjects != null){
                MessageObject object = new MessageObject(timeStamp, message, flag);
                messageObjects.add(object);
                mDao.updateUserMessageList(messageObjects, userId);
            }
            return null;
        }
    }


    public static class GetConversation extends AsyncTask<String, Void, ArrayList<MessageObject>> {

        private ChatBotDao mDao;
        private MutableLiveData<ArrayList<MessageObject>> mutableList;

        GetConversation(ChatBotDao dao, MutableLiveData<ArrayList<MessageObject>> mutableLiveData) {
            mDao = dao;
            mutableList = mutableLiveData;
        }

        @Override
        protected ArrayList<MessageObject> doInBackground(String... params) {
            String userId = params[0];
            String externalId = params[1];
            UserEntity userEntity = mDao.getUserChatConvo(userId);
            if (userEntity != null){
                return userEntity.getMessageList();
            }else {
                UserEntity insertUser = new UserEntity();
                insertUser.setUserId(userId);
                insertUser.setExternalId(externalId);
                insertUser.setMessageList(new ArrayList<>());
                mDao.save(insertUser);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MessageObject> result) {
            if (result != null && !result.isEmpty()){
                mutableList.postValue(result);
            }
        }
    }

}
