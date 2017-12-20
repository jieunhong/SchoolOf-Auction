package com.example.emirim.schoolauctionreal;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * create an instance of this fragment.
 */
public class MyPageFragment extends android.support.v4.app.Fragment {
    private TextView name_field;
    private TextView phone_field;
    private TextView school_field;
    private Button edit_button;
    private DatabaseReference mDatabase;


    public MyPageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        name_field = (TextView)view.findViewById(R.id.my_name_field);
        school_field = (TextView)view.findViewById(R.id.my_school_field);
        phone_field = (TextView)view.findViewById(R.id.my_phone_field);

        edit_button = (Button)view.findViewById(R.id.edit_button);

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
                Intent intent = new Intent(getActivity(), Edit_MyPage_Activity.class);
                startActivity(intent);
            }
        }
        );

        return view;
    }

}
