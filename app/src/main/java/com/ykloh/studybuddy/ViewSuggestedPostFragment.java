package com.ykloh.studybuddy;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by LYK on 12/8/2016.
 */

public class ViewSuggestedPostFragment extends Fragment {
    View thisView;

    ImageLoader.ImageCache imageCache = new BitmapLruCache();
    ImageLoader imageLoader;
    private NetworkImageView profilePicture;
    private TextView nameTV;
    private TextView titleTV;
    private TextView detailsTV;
    private TextView subjectTagsTV;
    private Button acceptButton;
    private Button dismissButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.view_suggested_post_fragment, container, false);
        getActivity().setTitle("Request");

        profilePicture = (NetworkImageView) thisView.findViewById(R.id.viewSuggestedPostProfilePicNIV);
        nameTV = (TextView) thisView.findViewById(R.id.viewSuggestedPostName);
        titleTV = (TextView) thisView.findViewById(R.id.viewSuggestedPostTitle);
        detailsTV = (TextView) thisView.findViewById(R.id.viewSuggestedPostDetails);
        subjectTagsTV = (TextView) thisView.findViewById(R.id.viewSuggestedPostSubjectTags);
        acceptButton = (Button) thisView.findViewById(R.id.viewSuggestedPostAcceptButton);
        dismissButton = (Button) thisView.findViewById(R.id.viewSuggestedPostDismissButton);

        final Bundle bundle = getArguments();
        String profilePictureUrl = bundle.getString("picture");
        String name = bundle.getString("name");
        String title = bundle.getString("title");
        String detail = bundle.getString("detail");
        String ownerID = bundle.getString("ownerID");
        final String postID = bundle.getString("postID");

        imageLoader = new ImageLoader(Volley.newRequestQueue(thisView.getContext()), imageCache);
        profilePicture.setImageUrl(profilePictureUrl, imageLoader);
        nameTV.setText(name);
        titleTV.setText(title);
        detailsTV.setText(detail);

        ViewSubjectTagsGetter viewSubjectTagsGetter = new ViewSubjectTagsGetter();
        viewSubjectTagsGetter.load(thisView.getContext(), postID, subjectTagsTV);

        this.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptSuggestion acceptSuggestion = new AcceptSuggestion();
                acceptSuggestion.submitHelp(thisView.getContext(), postID, bundle);

            }
        });

        this.dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(thisView.getContext());
                EmptyBuilder.setMessage("Are you sure you don't want to help?")
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DismissSuggestion dismissSuggestion = new DismissSuggestion();
                                dismissSuggestion.submitDismiss(thisView.getContext(), postID, bundle);
                            }
                        })
                        .setPositiveButton("NO", null)
                        .create()
                        .show();

            }
        });




        return thisView;
    }

}
