package com.ykloh.studybuddy;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYK on 10/14/2016.
 */

public class RequestFragment extends Fragment {


    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        thisView = inflater.inflate(R.layout.request_fragment, container, false);
        getActivity().setTitle("Request");

        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        ListView listView = (ListView) thisView.findViewById(R.id.requestListView);
        RequestGetter requestGetter = new RequestGetter();
        requestGetter.RequestLoader(thisView.getContext(), userID, listView);




        return thisView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);
        ListView listView = (ListView) thisView.findViewById(R.id.requestListView);
        RequestGetter requestGetter = new RequestGetter();
        requestGetter.RequestLoader(thisView.getContext(), userID, listView);

    }

}
