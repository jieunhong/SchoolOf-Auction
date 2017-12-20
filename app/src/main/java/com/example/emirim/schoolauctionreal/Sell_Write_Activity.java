package com.example.emirim.schoolauctionreal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emirim.schoolauctionreal.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Sell_Write_Activity extends AppCompatActivity {

    private static final String TAG = "Sell_Write_Activity";
    private static final String REQUIRED = "입력해주세요";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mTitleField;
    private EditText mBodyField;
    private Button mSubmitButton;
    private Spinner mCategory;
    private EditText mPrice;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_sell);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mTitleField = (EditText) findViewById(R.id.field_title);
        mBodyField = (EditText) findViewById(R.id.field_body);
        mCategory = (Spinner)findViewById(R.id.field_category);
        mPrice = (EditText)findViewById(R.id.field_price);


        mSubmitButton = (Button) findViewById(R.id.fab_submit_post);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();
        final String categoryText = mCategory.getSelectedItem().toString();

        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }
        if (TextUtils.isEmpty(categoryText)) {
            Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요",Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(mPrice.getText().toString())) {
            mPrice.setError(REQUIRED);
            return;
        }
        final int price = Integer.parseInt(mPrice.getText().toString());


        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "게시글을 등록하였습니다", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId =GetUserId.getUid();
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get user value
                // Write new post
                String username = dataSnapshot.child("name").getValue(String.class);
                String school = dataSnapshot.child("school").getValue(String.class);
                String phone = dataSnapshot.child("phone").getValue(String.class);
                writeNewPost(userId, username,school,phone, title, body, categoryText,price);

                setEditingEnabled(true);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username,String school, String phone, String title, String body, String category, int price) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("/"+userId+"/").push().getKey();
        Post post = new Post(true,userId, username,school,phone, title, body, category,price);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/board_posts/" + school +"/"+userId +"/"+key, postValues);
        childUpdates.put("/board_user-posts/" + userId +"/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}
