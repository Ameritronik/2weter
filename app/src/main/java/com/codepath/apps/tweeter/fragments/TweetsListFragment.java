package com.codepath.apps.tweeter.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.activities.EndlessRecyclerViewScrollListener;
import com.codepath.apps.tweeter.adapters.ComplexRecyclerAdapter;
import com.codepath.apps.tweeter.models.Tweet;

import java.util.ArrayList;

public abstract class TweetsListFragment extends android.support.v4.app.Fragment {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>();
    ComplexRecyclerAdapter aTweets;
    RecyclerView rvTweets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_tweets_list, parent,false);
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        // Tried different combinations of the following layout manager instances:
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
         rvTweets.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
        //mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //Log.d("DEBUG","LoadMore: page "+page+" Ct: "+totalItemsCount);
                long mUid = (tweets.get(totalItemsCount-1).getUid()-1);
                //Log.d("DEBUG","LoadMore: page "+page+" Ct: "+totalItemsCount+" uId: "+mUid);
                loadNextDataFromApi(mUid);
            }
        });
        // Adds the scroll listener to RecyclerView
        rvTweets.setAdapter(aTweets);
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
        populateTimeline(offset);
        aTweets.notifyDataSetChanged();
    }

    public void mAddAll(ArrayList<Tweet> twets) {
        Log.d("DEBUG","TLFR got twets list count as "+twets.size());
        tweets.addAll(twets);
        //Log.d("DEBUG","TLFR Added to tweets count: "+tweets.size());
        aTweets.notifyDataSetChanged();
        //mScrollListener.resetState();
        Log.d("DEBUG", "Done with tweets.addall");
    }
    public void mAddOneTweet(Tweet mitweet) {
        Log.d("DEBUG","in TweetLFrag, got miTweet "+ mitweet.toString());
        aTweets.addTweet(mitweet);
        aTweets.notifyDataSetChanged();
        Log.d("DEBUG", "Added One Tweet on top of list");
    }
    // Abstract method to be overridden
    protected abstract void populateTimeline(long maxId);
}
