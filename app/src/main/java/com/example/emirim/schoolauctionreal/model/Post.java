package com.example.emirim.schoolauctionreal.model;

/**
 * Created by Eun bee on 2016-delete_things-19.
 */
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Post {

    public String uid;
    public String author;
    public String title;
    public String body;
    public String school;
    public String phone;
    public String category;
    public int price;
    public boolean buyOrSell;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(boolean buyOrSell, String uid, String author,String school, String phone, String title, String body,String category, int price) {
        this.buyOrSell = buyOrSell;
        this.uid = uid;
        this.author = author;
        this.school = school;
        this.title = title;
        this.body = body;
        this.phone = phone;
        this.category = category;
        this.price = price;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("buyOrSell",buyOrSell);
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("school",school);
        result.put("phone",phone);
        result.put("category",category);
        result.put("price",price);

        return result;
    }
    // [END post_to_map]

}
// [END post_class]