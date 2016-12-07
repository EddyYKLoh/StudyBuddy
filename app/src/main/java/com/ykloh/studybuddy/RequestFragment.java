package com.ykloh.studybuddy;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LYK on 10/14/2016.
 */

public class RequestFragment extends Fragment {

    List<PublicPost> list = new ArrayList<PublicPost>();

    View thisView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.request_fragment, container, false);
        getActivity().setTitle("Requests");

        String postString = "";

        Bundle bundle = getArguments();
        if (bundle != null) {
            postString = bundle.getString("RequestList");
        }


        if (postString.equals("")) {

        } else {
            String[] individualPost = postString.split(System.getProperty("line.separator"));

            for (int i = 0; i < individualPost.length; i++) {
                String[] postElements = individualPost[i].split("<SEPARATE>");
                list.add(new PublicPost(postElements[0], postElements[1], postElements[2], postElements[3], postElements[4], postElements[5]));
            }

            final RequestAdapter adapter = new RequestAdapter(getActivity(), list);
            ListView listView = (ListView) thisView.findViewById(R.id.requestListView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PublicPost publicPost = (PublicPost) parent.getItemAtPosition(position);
                    PublicPostViewerPicker publicPostViewerPicker = new PublicPostViewerPicker(publicPost.getProfilePictureUrl()
                            , publicPost.getUserName()
                            , publicPost.getPublicPostTitle()
                            , publicPost.getDetails()
                            , publicPost.getOwnerID()
                            , publicPost.getPostID()
                            , thisView.getContext());


                }
            });
        }

        return thisView;
    }



}
