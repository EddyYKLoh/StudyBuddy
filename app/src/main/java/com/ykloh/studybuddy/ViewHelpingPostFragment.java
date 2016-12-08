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
 * Created by LYK on 12/7/2016.
 */

public class ViewHelpingPostFragment extends Fragment {
    View thisView;

    ImageLoader.ImageCache imageCache = new BitmapLruCache();
    ImageLoader imageLoader;
    private NetworkImageView profilePicture;
    private TextView nameTV;
    private TextView titleTV;
    private TextView detailsTV;
    private TextView subjectTagsTV;
    private Button completedButton;
    private Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.view_helping_post_fragment, container, false);
        getActivity().setTitle("Request");

        profilePicture = (NetworkImageView) thisView.findViewById(R.id.viewHelpPostProfilePicNIV);
        nameTV = (TextView) thisView.findViewById(R.id.viewHelpPostName);
        titleTV = (TextView) thisView.findViewById(R.id.viewHelpPostTitle);
        detailsTV = (TextView) thisView.findViewById(R.id.viewHelpPostDetails);
        subjectTagsTV = (TextView) thisView.findViewById(R.id.viewHelpPostSubjectTags);
        completedButton = (Button) thisView.findViewById(R.id.viewHelpPostCompletedButton);
        cancelButton = (Button) thisView.findViewById(R.id.viewHelpPostCancelButton);

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

        this.completedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteHelp completeHelp = new CompleteHelp();
                completeHelp.submitCompleteHelp(thisView.getContext(), postID, bundle);

            }
        });

        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(thisView.getContext());
                EmptyBuilder.setMessage("Are you sure you want to cancel?")
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CancelHelp cancelHelp = new CancelHelp();
                                cancelHelp.submitCancelHelp(thisView.getContext(), postID, bundle);
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
