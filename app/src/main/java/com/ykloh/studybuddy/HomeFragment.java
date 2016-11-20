package com.ykloh.studybuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYK on 10/13/2016.
 */

public class HomeFragment extends Fragment {

    List<PublicPost> list = new ArrayList<PublicPost>();

    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.home_fragment, container, false);
        getActivity().setTitle("Home");
        FloatingActionButton fab = (FloatingActionButton) thisView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentFrame, new PostPublicRequestFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        String postString = "";

        Bundle bundle = getArguments();
        if (bundle != null) {
            postString = bundle.getString("PublicPostList");
        }


        if (postString.equals("")) {

        } else {
            String[] individualPost = postString.split(System.getProperty("line.separator"));

            for (int i = 0; i < individualPost.length; i++) {
                String[] postElements = individualPost[i].split("<SEPARATE>");
                list.add(new PublicPost(postElements[1], postElements[0], postElements[2]));
            }

            final RequestAdapter adapter = new RequestAdapter(getActivity(), list);
            ListView listView = (ListView) thisView.findViewById(R.id.homeListView);
            listView.setAdapter(adapter);
        }

        return thisView;

    }


}
