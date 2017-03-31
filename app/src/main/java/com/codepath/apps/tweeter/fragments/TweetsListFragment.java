package com.codepath.apps.tweeter.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.activities.EndlessRecyclerViewScrollListener;
import com.codepath.apps.tweeter.adapters.ComplexRecyclerAdapter;
import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.models.User;

import java.util.ArrayList;
import java.util.Date;

public class TweetsListFragment extends android.support.v4.app.Fragment {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    ComplexRecyclerAdapter aTweets;
    RecyclerView rvTweets;
    private Tweet myTweet = new Tweet();
    EndlessRecyclerViewScrollListener mScrollListener;

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

//    public void dataBack(final String twBody, String twURL) {
//        //Toast.makeText(this, "Body: " + twBody +" URL: "+twURL, Toast.LENGTH_SHORT).show();
//        client.setMyTweet(twBody);
//        client.settweetUser_id(twURL);
//        client.postHomeTimeline(new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                //showCustomToast("Tweet successful", "GREEN");
//                makeMyTweet(twBody);
//                aTweets.addTweet(myTweet);
//                aTweets.notifyDataSetChanged();
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                //showCustomToast("Tweet unsuccessful","RED");
//            }
//        });
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent,false);
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        // Tried different combinations of the following layout manager instances:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rvTweets.getContext());
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        //linearLayoutManager.scrollToPosition(0);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(aTweets);
        // Retain an instance so that you can call `resetState()` for fresh searches
        //rvTweets.addOnScrollListener( new EndlessRecyclerViewScrollListener(linearLayoutManager) {
        mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //Log.d("DEBUG","LoadMore: page "+page+" Ct: "+totalItemsCount);
                long mUid = (tweets.get(totalItemsCount-1).getUid()-1);
                //Log.d("DEBUG","LoadMore: page "+page+" Ct: "+totalItemsCount+" uId: "+mUid);
                loadNextDataFromApi(mUid);
            }
        };

        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(mScrollListener);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create the arraylist from data
        tweets = new ArrayList<>();
        //Construct the adapter from data source
        aTweets = new ComplexRecyclerAdapter(getActivity(), tweets);
    }


    public void loadNextDataFromApi(Long offset) {
        //firstTweetAccess = false;
        //showCustomToast("Getting more tweets...", "BLUE");
        //showToast(getBaseContext(),"Getting more tweets");
        int mTweetSize = tweets.size() - 1;
        //rvTweets.notifyItemRangeInserted(mSize, mArticleSize);
        rvTweets.setItemViewCacheSize(mTweetSize); // Should this mbe mSize?
        //populateTimeline(offset);
        aTweets.notifyDataSetChanged();
    }

    public void mAddAll(ArrayList<Tweet> twets) {
        Log.d("DEBUG","TLFR got twets list count as "+twets.size());
        tweets.addAll(twets);
        Log.d("DEBUG","TLFR Added to tweets count: "+tweets.size());
        aTweets.notifyDataSetChanged();
        mScrollListener.resetState();
        Log.d("DEBUG", "Done with tweets.addall");
    }
}
