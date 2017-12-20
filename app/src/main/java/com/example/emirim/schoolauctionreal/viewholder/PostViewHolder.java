package com.example.emirim.schoolauctionreal.viewholder;

/**
 * Created by Eun bee on 2016-delete_things-19.
 */


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emirim.schoolauctionreal.model.Post;

import mirim.PoliticTeens_Client.R;
import mirim.PoliticTeens_Client.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public TextView categoryView;

    public PostViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
        authorView = (TextView) itemView.findViewById(R.id.author);
        categoryView = (TextView) itemView.findViewById(R.id.category);
    }

    public void bindToPost(Post post) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        categoryView.setText(post.category);
    }
}