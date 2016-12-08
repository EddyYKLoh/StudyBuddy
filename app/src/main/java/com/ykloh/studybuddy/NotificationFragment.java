package com.ykloh.studybuddy;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by LYK on 12/9/2016.
 */
public class NotificationFragment extends Fragment {



    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        thisView = inflater.inflate(R.layout.notification_fragment, container, false);
        getActivity().setTitle("Notification");

        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        ListView listView = (ListView) thisView.findViewById(R.id.notificationListView);
        NotificationGetter notificationGetter = new NotificationGetter();
        notificationGetter.notificationLoader(thisView.getContext(), userID, listView);


        return thisView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);
        ListView listView = (ListView) thisView.findViewById(R.id.notificationListView);
        NotificationGetter notificationGetter = new NotificationGetter();
        notificationGetter.notificationLoader(thisView.getContext(), userID, listView);

    }

}
