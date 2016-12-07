package com.ykloh.studybuddy;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ViewPublicPostFragment extends Fragment {
    View thisView;

    ImageLoader.ImageCache imageCache = new BitmapLruCache();
    ImageLoader imageLoader;
    private NetworkImageView profilePicture;
    private TextView nameTV;
    private TextView titleTV;
    private TextView detailsTV;
    private TextView subjectTagsTV;
    private Button helpButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.view_public_post_fragment, container, false);
        getActivity().setTitle("Request");

        profilePicture = (NetworkImageView) thisView.findViewById(R.id.viewPubPostProfilePicNIV);
        nameTV = (TextView) thisView.findViewById(R.id.viewPubPostName);
        titleTV = (TextView) thisView.findViewById(R.id.viewPubPostTitle);
        detailsTV = (TextView) thisView.findViewById(R.id.viewPubPostDetails);
        subjectTagsTV = (TextView) thisView.findViewById(R.id.viewPubPostSubjectTags);
        helpButton = (Button) thisView.findViewById(R.id.viewPubPostHelpButton);

        Bundle bundle = getArguments();
        String profilePictureUrl = bundle.getString("picture");
        String name = bundle.getString("name");
        String title = bundle.getString("title");
        String detail = bundle.getString("detail");
        String ownerID = bundle.getString("ownerID");
        String postID = bundle.getString("postID");

        imageLoader = new ImageLoader(Volley.newRequestQueue(thisView.getContext()), imageCache);
        profilePicture.setImageUrl(profilePictureUrl, imageLoader);
        nameTV.setText(name);
        titleTV.setText(title);
        detailsTV.setText(detail);

        ViewSubjectTagsGetter viewSubjectTagsGetter = new ViewSubjectTagsGetter();
        viewSubjectTagsGetter.load(thisView.getContext(), postID, subjectTagsTV);










        return thisView;
    }
}