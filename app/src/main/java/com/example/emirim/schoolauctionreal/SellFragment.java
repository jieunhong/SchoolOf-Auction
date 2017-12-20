package com.example.emirim.schoolauctionreal;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emirim.schoolauctionreal.model.Post;
import com.example.emirim.schoolauctionreal.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


public class SellFragment extends android.support.v4.app.Fragment {

    android.support.v4.app.FragmentManager manager;
    DatabaseReference mDatabase;
    RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    Query postsQuery;
    String school="";

    public SellFragment() {
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
        View view =inflater.inflate(R.layout.fragment_sell, container, false);

        manager = (android.support.v4.app.FragmentManager)getFragmentManager();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecycler = (RecyclerView) view.findViewById(R.id.board_list);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        postsQuery = getQuery1(mDatabase);


        return view;
    }



    // [END post_stars_transaction]

    public FirebaseRecyclerAdapter<Post, PostViewHolder> setAdapter_own(FirebaseRecyclerAdapter<Post, PostViewHolder> adapter){
        adapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.item_post,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, final int position) {
                final DatabaseReference postRef = getRef(position);
                // Set click listener for the whole post view
                final String postKey = postRef.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Launch PostDetailActivity
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });
                viewHolder.bindToPost(model);
            }


        };
        return adapter;
    }
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public Query getQuery1(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                school = dataSnapshot.child("board_posts/").child(GetUserId.getUid()).child("school").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query recentPostsQuery = databaseReference.child("board_posts/"+school)
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }




}
