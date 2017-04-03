package com.codepath.apps.tweeter.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hkanekal on 3/30/2017.
 */

public class MentionsTimeLineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = com.codepath.apps.tweeter.activities.TwitterApplication.getRestClient();
        if(com.codepath.apps.tweeter.network.checknetwork.isOnline()) {
            populateTimeline(1);
        }
    }

    // Send API request to get the timeline json
    // Fill the list view by creating the tweet objects from json
    @Override
    public void populateTimeline(long uId) {
        client.setId(uId);
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
            // Successful
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //super.onSuccess(statusCode, headers, response);
                //Log.d("DEBUG",json.toString()); // Got JSON here
                mAddAll(Tweet.fromJSONArray(json));
            }

            // failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
                String JsonErrorMessage = "Json message corrupted";
                Log.d("DEBUG", JsonErrorMessage);
                //showToast(getBaseContext(), JsonErrorMessage);
            }
        });
    }
    public static MentionsTimeLineFragment newInstance(int i, String mentions) {
        MentionsTimeLineFragment mtfm = new MentionsTimeLineFragment();
        return mtfm;
    }
}
