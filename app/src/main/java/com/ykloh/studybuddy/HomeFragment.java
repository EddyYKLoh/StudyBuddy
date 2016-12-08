package com.ykloh.studybuddy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYK on 10/13/2016.
 */

public class HomeFragment extends Fragment {

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

        ListView listView = (ListView) thisView.findViewById(R.id.homeListView);

        AllPublicPostGetter publicPostGetter = new AllPublicPostGetter();
        publicPostGetter.PublicPostLoader(thisView.getContext(), listView);

        return thisView;

    }
    @Override
    public void onResume() {
        super.onResume();

        ListView listView = (ListView) thisView.findViewById(R.id.homeListView);
        AllPublicPostGetter publicPostGetter = new AllPublicPostGetter();
        publicPostGetter.PublicPostLoader(thisView.getContext(), listView);

    }

}
