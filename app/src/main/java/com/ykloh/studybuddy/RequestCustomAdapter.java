package com.ykloh.studybuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by LYK on 10/14/2016.
 */

public class RequestCustomAdapter extends ArrayAdapter<PublicPost> {

    private final List<PublicPost> list;
    Context context;

    public RequestCustomAdapter(Context context, List<PublicPost> list) {
        super(context, R.layout.request_row, list);
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
        RequestViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.request_row, parent, false);
            viewHolder = new RequestViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.requestOwnerName);
            viewHolder.postTitle = (TextView) convertView.findViewById(R.id.requestTitle);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(RequestViewHolder)convertView.getTag();
        }
        viewHolder.userName.setText(list.get(position).getUserName());
        viewHolder.postTitle.setText(list.get(position).getPublicPostTitle());
        return convertView;
    }
}
