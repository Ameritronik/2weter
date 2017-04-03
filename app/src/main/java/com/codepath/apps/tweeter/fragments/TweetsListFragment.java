package com.codepath.apps.tweeter.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        showCustomToast("Fetching.....");
        int mTweetSize = tweets.size() - 1;
        rvTweets.setItemViewCacheSize(mTweetSize);
        populateTimeline(offset);
        aTweets.notifyDataSetChanged();
    }

    public void mAddAll(ArrayList<Tweet> twets) {
        tweets.addAll(twets);
        aTweets.notifyDataSetChanged();
    }
    public void mAddOneTweet(Tweet mitweet) {
        aTweets.addTweet(mitweet);
        aTweets.notifyDataSetChanged();
    }
    // Abstract method to be overridden
    protected abstract void populateTimeline(long maxId);
    public void showCustomToast(String message) {
        Toast toast= Toast.makeText(getContext(), message,Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        toastView.setBackgroundResource(R.drawable.tags_rounded_corners);
        toastView.setBackgroundColor(Color.rgb(43,155,247));
        TextView textView = (TextView) toastView.findViewById(android.R.id.message);
        textView.setShadowLayer(0,0,0, Color.TRANSPARENT);
        textView.setTextColor(Color.WHITE);
        toast.setView(toastView);
        toast.setGravity(Gravity.TOP, 50, 600);
        toast.show();
    }

}
