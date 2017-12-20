package com.example.emirim.schoolauctionreal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by fr on 2017-12-19.
 */

public class Edit_MyPage_Activity extends Activity {

    DatabaseReference mDatabase;

    private TextView name_field;
    private TextView phone_field;
    private TextView school_field;
    private Button edit_button;
    private String id="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mypage);

        name_field = (TextView)findViewById(R.id.signin_activity_name_field);
        school_field = (TextView)findViewById(R.id.signin_activity_school_field);
        phone_field = (TextView)findViewById(R.id.signin_activity_phone_field);
        edit_button = (Button)findViewById(R.id.ok_button);


        mDatabase = FirebaseDatabase.getInstance().getReference();;

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("sda", GetUserId.getUid());
                name_field.setText(dataSnapshot.child("users").child(GetUserId.getUid()).child("name").getValue(String.class));
                school_field.setText(dataSnapshot.child("users").child(GetUserId.getUid()).child("school").getValue(String.class));
                phone_field.setText(dataSnapshot.child("users").child(GetUserId.getUid()).child("phone").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_field.getText().toString();
                String phone = phone_field.getText().toString().trim().replace("-","");
                String school =school_field.getText().toString();
                validateForm();
                writeNewUser(GetUserId.getUid(),name,school,phone);

                Intent intent = new Intent(Edit_MyPage_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(name_field.getText().toString()) || TextUtils.isEmpty(school_field.getText().toString())
                || TextUtils.isEmpty(phone_field.getText().toString())) {
            Toast.makeText(getApplicationContext(),"입력해주세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        return result;
    }

    // [START basic_write]
    private void writeNewUser(final String userId, String name, String school, String phone) {
        mDatabase.child("users").child(userId).child("name").setValue(name);
        mDatabase.child("users").child(userId).child("school").setValue(school);
        mDatabase.child("users").child(userId).child("phone").setValue(phone);
    }
}
