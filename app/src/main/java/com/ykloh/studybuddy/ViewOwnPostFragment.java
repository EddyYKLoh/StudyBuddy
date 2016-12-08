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
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ViewOwnPostFragment extends Fragment {
    View thisView;

    ImageLoader.ImageCache imageCache = new BitmapLruCache();
    ImageLoader imageLoader;
    private NetworkImageView profilePicture;
    private TextView nameTV;
    private TextView titleTV;
    private TextView detailsTV;
    private TextView subjectTagsTV;
    private Button endButton;
    private ImageButton deleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.view_own_post_fragment, container, false);
        getActivity().setTitle("Request");

        profilePicture = (NetworkImageView) thisView.findViewById(R.id.viewOwnPostProfilePicNIV);
        nameTV = (TextView) thisView.findViewById(R.id.viewOwnPostName);
        titleTV = (TextView) thisView.findViewById(R.id.viewOwnPostTitle);
        detailsTV = (TextView) thisView.findViewById(R.id.viewOwnPostDetails);
        subjectTagsTV = (TextView) thisView.findViewById(R.id.viewOwnPostSubjectTags);
        endButton = (Button) thisView.findViewById(R.id.viewOwnPostEndButton);
        deleteButton = (ImageButton) thisView.findViewById(R.id.viewOwnPostDeleteButton);

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

        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(thisView.getContext());
                EmptyBuilder.setMessage("Are you sure you want to end?")
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeletePost deletePost = new DeletePost();
                                deletePost.submitDelete(thisView.getContext(), postID);
                            }
                        })
                        .setPositiveButton("NO", null)
                        .create()
                        .show();

            }
        });

        this.endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EmptyBuilder = new AlertDialog.Builder(thisView.getContext());
                EmptyBuilder.setMessage("Are you sure you don't need help anymore?")
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EndPost endPost = new EndPost();
                                endPost.confirmEnd(thisView.getContext(), postID);
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