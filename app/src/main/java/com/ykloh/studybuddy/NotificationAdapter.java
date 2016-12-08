package com.ykloh.studybuddy;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by LYK on 12/9/2016.
 */
public class NotificationAdapter extends ArrayAdapter<Notification>

{

    private final List<Notification> list;
    Context context;

    public NotificationAdapter(Context context, List<Notification> list) {
        super(context, R.layout.notification_row, list);
        this.context = context;
        this.list = list;

    }

    static class NotificationViewHolder {
        protected TextView notification;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationAdapter.NotificationViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.notification_row, parent, false);
            viewHolder = new NotificationAdapter.NotificationViewHolder();
            viewHolder.notification = (TextView) convertView.findViewById(R.id.notificationRow);


            convertView.setTag(viewHolder);
        }else{
            viewHolder=(NotificationAdapter.NotificationViewHolder)convertView.getTag();
        }
        viewHolder.notification.setText(list.get(position).getNotification());


        return convertView;
    }
}
