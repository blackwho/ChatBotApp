package com.example.docsapp.chatbotapp.Repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.docsapp.chatbotapp.Data.MessageModel;
import com.example.docsapp.chatbotapp.Network.GsonRequest;
import com.example.docsapp.chatbotapp.Network.VolleySingleton;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AppRepository {

    private static final String TAG = AppRepository.class.getSimpleName();
    private static AppRepository mInstance;
    private RequestQueue mRequestQueue;
    private Context context;
    private HashMap<String, String> queryMap;
    private MutableLiveData<MessageModel> mutableMessageModel;

    private AppRepository(Application application) {
        mRequestQueue = VolleySingleton.getInstance(application.getApplicationContext()).getRequestQueue();
        this.context = application.getApplicationContext();
        mutableMessageModel = new MutableLiveData<>();
        queryMap = new HashMap<>();
        queryMap.put("apiKey", "6nt5d1nJHkqbkphe");
        queryMap.put("chatBotID", String.valueOf(63906));
        queryMap.put("externalID", "vishal");
    }

    public static AppRepository getInstance(Application application){
        if(mInstance == null){
            mInstance = new AppRepository(application);
        }
        return mInstance;
    }

    public MutableLiveData<MessageModel> getMutableMessageModel(){
        return mutableMessageModel;
    }

    public void sendMessageToBot(String message){
        String URL = urlBuilder(message);
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
                            MessageModel messageModel = new MessageModel(messageBody, "received");
                            mutableMessageModel.postValue(messageModel);
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

    private String urlBuilder(String message){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.personalityforge.com")
                .appendPath("api")
                .appendPath("chat")
                .appendQueryParameter("apiKey", "6nt5d1nJHkqbkphe")
                .appendQueryParameter("message", message)
                .appendQueryParameter("chatBotID", String.valueOf(63906))
                .appendQueryParameter("externalID", "chirag1");
        return builder.build().toString();

    }


}
