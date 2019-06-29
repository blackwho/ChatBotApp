package com.example.docsapp.chatbotapp.Network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class VolleySingleton {
    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
//            HttpStack stack = null;
//            try {
//                stack = new HurlStack(null, new TLSSocketFactory());
//            } catch (KeyManagementException e) {
//                e.printStackTrace();
//                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
//                stack = new HurlStack();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//                Log.d("Your Wrapper Class", "Could not create new stack for TLS v1.2");
//                stack = new HurlStack();
//            }
//            mRequestQueue = Volley.newRequestQueue(mCtx, stack);
//        } else {
//            mRequestQueue = Volley.newRequestQueue(mCtx);
//        }
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

}

