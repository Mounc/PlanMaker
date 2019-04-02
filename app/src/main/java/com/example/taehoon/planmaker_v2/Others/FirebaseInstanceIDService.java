//package com.example.taehoon.planmaker_v2.Others;
//
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
///**
// * Created by TAEHOON on 2017-06-08.
// */
//
//public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFriebaseIIDService";
//
//    @Override
//    public void onTokenRefresh() {
////        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        sendRegistrationToServer(refreshedToken);
//    }
//
//    private void sendRegistrationToServer(String refreshedToken) {
//    }
//}
