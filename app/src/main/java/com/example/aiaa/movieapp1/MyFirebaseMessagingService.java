package com.example.aiaa.movieapp1;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG ="MyFirebaseMessagingService";

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        sendRegistrationToServer(token);

    }
}
