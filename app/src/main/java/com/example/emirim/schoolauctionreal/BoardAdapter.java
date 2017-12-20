package com.example.emirim.schoolauctionreal;

import android.content.Context;
import android.support.annotation.AnyRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emirim.schoolauctionreal.model.Post;

import java.util.ArrayList;

/**
 * Created by dodo1 on 2017-12-17.
 */
public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> list;

    public TextView titleView;
    public TextView authorView;
    public TextView categoryView;

    public BoardAdapter(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.title);
        authorView = (TextView) itemView.findViewById(R.id.author);
        categoryView = (TextView)itemView.findViewById(R.id.category);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);

    }
    /*
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String item = getItem(position);

        holder.title.setText(item);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String getItem(int position) {
        return String.valueOf(list.get(position));
    }

    public void clear() {
        if(null != list) {
            list.clear();
        }
    }
    public void addAll(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;

        public ViewHolder(View parent) {
            super(parent);

            title= (TextView) parent.findViewById(R.id.title);
            content=(TextView)parent.findViewById(R.id.content);
        }
    }*/
}


