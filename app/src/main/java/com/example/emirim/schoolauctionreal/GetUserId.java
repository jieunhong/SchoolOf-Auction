package com.example.emirim.schoolauctionreal;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by User on 2017-12-18.
 */

public class GetUserId {
    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
