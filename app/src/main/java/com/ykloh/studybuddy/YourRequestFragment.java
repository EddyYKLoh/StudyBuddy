package com.ykloh.studybuddy;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by LYK on 12/8/2016.
 */
public class YourRequestFragment extends Fragment {
    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        thisView = inflater.inflate(R.layout.your_request_fragment, container, false);
        getActivity().setTitle("Your Request");

        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);

        ListView listView = (ListView) thisView.findViewById(R.id.yourRequestListView);
       YourRequestGetter yourRequestGetter = new YourRequestGetter();
        yourRequestGetter.yourRequestLoader(thisView.getContext(), userID, listView);


        return thisView;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = thisView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("userID", null);
        ListView listView = (ListView) thisView.findViewById(R.id.yourRequestListView);
        YourRequestGetter yourRequestGetter = new YourRequestGetter();
        yourRequestGetter.yourRequestLoader(thisView.getContext(), userID, listView);

    }

}
