package com.example.dodo1305.schoolauctionreal.model;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String name;
    public String school;
    public String phone;
    public String id;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String school, String phone) {
        this.name = name;
        this.id = id;
        this.school = school;
        this.phone = phone;

    }

}
// [END blog_user_class]
