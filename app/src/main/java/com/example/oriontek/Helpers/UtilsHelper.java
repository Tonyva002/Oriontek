package com.example.oriontek.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UtilsHelper {
    public static DatabaseReference getDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }

}

