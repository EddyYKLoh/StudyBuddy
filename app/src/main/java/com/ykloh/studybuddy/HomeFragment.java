package com.ykloh.studybuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


        return thisView;

    }


}
