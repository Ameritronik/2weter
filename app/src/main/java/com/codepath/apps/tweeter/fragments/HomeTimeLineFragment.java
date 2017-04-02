package com.codepath.apps.tweeter.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import com.codepath.apps.tweeter.activities.TwitterApplication;
import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.models.User;
import com.codepath.apps.tweeter.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class HomeTimeLineFragment extends TweetsListFragment {
    private TwitterClient client;
    private Tweet myTweet = new Tweet();
    private String MYTWEETID = "@pjaytumkur";
    private String tBody;
    private String tURL;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = com.codepath.apps.tweeter.activities.TwitterApplication.getRestClient();
        //if(com.codepath.apps.tweeter.network.checknetwork.HaveCloud()) {
        populateTimeline(1);
        //}
    }

    // Send API request to get the timeline json
    // Fill the list view by creating the tweet objects from json
    @Override
    public void populateTimeline(long uId) {
        client.setId(uId);
        client.getHomeTimeline(new JsonHttpResponseHandler() {
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
    public void makeMyTweet(String twBody) {
        User iAm = new User();
        iAm.setName("HK");
        iAm.setScreenName("pjaytumkur");
        String id_str = "843926695107166208";
        long uid = Long.parseLong(id_str);
        iAm.setUid(uid);
        Uri uri = Uri.parse("android.resource://com.codepath.apps.tweet/drawable/mytwitimage");
        iAm.setProfileImageUrl(uri.toString());
        // Get 'now' time
        Date today = new Date();
        CharSequence cTime  = DateFormat.format("EEE MMM dd HH:mm:ss -0700 yyyy", today.getTime());
        // Compose my Tweet
        myTweet.setBody(twBody);
        myTweet.setUid(uid);
        myTweet.setCreatedAt(cTime.toString());
        myTweet.setUser(iAm);
        myTweet.settMediaType(null);
        myTweet.settMediaType(null);
        myTweet.settMediaprofileImageUrl("");
        myTweet.settMediaId(Long.valueOf(1));
    }
    public void dataBack(String twBody, String twURL) {
        //Toast.makeText(this, "Body: " + twBody +" URL: "+twURL, Toast.LENGTH_SHORT).show();
        client = TwitterApplication.getRestClient();
        tBody = twBody;// need these to prevent "static" shock :-)
        tURL = twURL; // need these to prevent "static" shock :-)
        client.setMyTweet(tBody);
        client.settweetUser_id(tURL);
        client.postHomeTimeline(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //showCustomToast("Tweet successful", "GREEN");
                makeMyTweet(tBody);
                //Log.d("DEBUG","HomeTimeLineFrag Inserting Tweet: "+myTweet.toString());
                mAddOneTweet(myTweet);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //showCustomToast("Tweet unsuccessful","RED");
            }
        });
    }

}
