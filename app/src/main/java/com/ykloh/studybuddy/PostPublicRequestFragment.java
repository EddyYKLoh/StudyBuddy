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

/**
 * Created by LYK on 10/12/2016.
 */

public class PostPublicRequestFragment extends Fragment {

    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.public_post_fragment, container, false);
        getActivity().setTitle("Create public post");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        final String userID = sharedPreferences.getString("userID", null);
        Button postPublicRequestNextButton = (Button) thisView.findViewById(R.id.publicPostNextButton);
        postPublicRequestNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText publicPostTitleEditText = (EditText) getActivity().findViewById(R.id.publicPostTitleEditText);
                EditText publicPostDetailsEditText = (EditText) getActivity().findViewById(R.id.publicPostDetailsEditText);
                final String publicPostTitle = publicPostTitleEditText.getText().toString().trim();
                final String publicPostDetails = publicPostDetailsEditText.getText().toString().trim();
                if (publicPostTitle.equals("") || publicPostDetails.equals("")) {
                    AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(getActivity());
                    EmptyBuilder.setMessage("Please complete the form.")
                            .setNegativeButton("OK", null)
                            .create()
                            .show();
                } else {
                    PublicPostUploader publicPostUploader = new PublicPostUploader();
                    publicPostUploader.UploadPublicPost(getActivity(), userID, publicPostTitle, publicPostDetails);
                    SubjectListGetter subjectListGetter = new SubjectListGetter();
                    subjectListGetter.GetSubjects(getActivity());
                }

            }
        });


        return thisView;
    }


}
