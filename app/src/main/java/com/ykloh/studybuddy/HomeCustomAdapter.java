package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by LYK on 11/9/2016.
 */

public class HomeCustomAdapter extends ArrayAdapter<PublicPost> {

    private final List<PublicPost> list;
    Context context;

    public HomeCustomAdapter(Context context, List<PublicPost> list) {
        super(context, R.layout.public_post_row, list);
        this.context = context;
        this.list = list;
    }

    static class RequestViewHolder {
        protected TextView userName;
        protected TextView postTitle;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestCustomAdapter.RequestViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.public_post_row, parent, false);
            viewHolder = new RequestCustomAdapter.RequestViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.publicPostOwnerNameListTemplate);
            viewHolder.postTitle = (TextView) convertView.findViewById(R.id.publicPostTitleListTemplate);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(RequestCustomAdapter.RequestViewHolder)convertView.getTag();
        }
        viewHolder.userName.setText(list.get(position).getUserName());
        viewHolder.postTitle.setText(list.get(position).getPublicPostTitle());
        return convertView;
    }
}
