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
 * Created by LYK on 11/9/2016.
 */

public class RequestAdapter extends ArrayAdapter<PublicPost> {

    private final List<PublicPost> list;
    Context context;
    ImageLoader.ImageCache imageCache = new BitmapLruCache();
    ImageLoader imageLoader;

    public RequestAdapter(Context context, List<PublicPost> list) {
        super(context, R.layout.request_row, list);
        this.context = context;
        this.list = list;
        imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
    }

    static class RequestViewHolder {
        protected TextView userName;
        protected TextView postTitle;
        protected NetworkImageView profilePicture;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestAdapter.RequestViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.request_row, parent, false);
            viewHolder = new RequestAdapter.RequestViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.publicPostOwnerNameListTemplate);
            viewHolder.postTitle = (TextView) convertView.findViewById(R.id.publicPostTitleListTemplate);
            viewHolder.profilePicture = (NetworkImageView) convertView.findViewById(R.id.profilePicturePublicPostImageView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder=(RequestAdapter.RequestViewHolder)convertView.getTag();
        }
        viewHolder.userName.setText(list.get(position).getUserName());
        viewHolder.postTitle.setText(list.get(position).getPublicPostTitle());
        viewHolder.profilePicture.setImageUrl(list.get(position).getProfilePictureUrl(), imageLoader);

        return convertView;
    }
}
