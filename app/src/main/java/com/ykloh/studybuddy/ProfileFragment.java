package com.ykloh.studybuddy;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by LYK on 12/9/2016.
 */

public class ProfileFragment extends Fragment {

    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.profile_fragment, container, false);
        getActivity().setTitle("Profile");

        NetworkImageView profileProfPicNIV = (NetworkImageView) thisView.findViewById(R.id.profileProfPicNIV);
        TextView profileNameTV = (TextView) thisView.findViewById(R.id.profileNameTV);
        RatingBar profileRatingBar = (RatingBar) thisView.findViewById(R.id.profileRatingBar);
        TextView profileEmailTV = (TextView) thisView.findViewById(R.id.profileEmailTV);

        final Bundle bundle = getArguments();
        String userID = bundle.getString("userID");

        ProfileContentLoader profileContentLoader = new ProfileContentLoader();
        profileContentLoader.loadContent(thisView.getContext(), userID, profileProfPicNIV, profileNameTV,profileRatingBar,profileEmailTV);


        return thisView;
    }


}
