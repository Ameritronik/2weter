package com.codepath.apps.tweeter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.viewholders.PhotoTweetViews;
import com.codepath.apps.tweeter.viewholders.SimpleTweetViews;

import java.util.ArrayList;

public class ComplexRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PHOTO = 2; // This tweet has a picture
    private final int VIDEO   = 1; // has video
    private final int NORMAL  = 0; // Normal tweet no pic or vid
    private static ArrayList<Tweet> tweets;
    private static Context mContext;

    // construct
    public ComplexRecyclerAdapter(Context context, ArrayList<Tweet> tweets) {
        this.tweets = tweets;
        mContext = context;
    }

    public static void addTweet(Tweet t) {
        tweets.add(0,t);
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("DEBUG","Got item count as "+this.tweets.size());
        return this.tweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        int retMtype = 0;
        Tweet thisTweet = tweets.get(position);
        String mType = thisTweet.gettMediaType();
        if(mType != null) {
            if (mType.equals("photo")) {
                retMtype = PHOTO;
            } else if (mType.equals("video")) {
                retMtype = VIDEO;
            }
        } else {
            retMtype =  NORMAL;
        }
        String rm = String.valueOf(retMtype);
        return retMtype;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        Log.d("DEBUG","Got viewType: "+viewType);
        switch (viewType) {
            case NORMAL:
                View textOnly = inflater.inflate(R.layout.simple_tweet_view,viewGroup, false);
                viewHolder = new SimpleTweetViews(mContext, textOnly, tweets);
                break;
            case PHOTO:
                View FullView = inflater.inflate(R.layout.photo_tweet_view,viewGroup, false);
                viewHolder = new PhotoTweetViews(mContext, FullView, tweets);
                break;
            default:
                FullView = inflater.inflate(R.layout.photo_tweet_view,viewGroup,false); //grid_view_video_tweet,viewGroup, false);
                viewHolder = new PhotoTweetViews(mContext, FullView, tweets);
                break;
        }
        Log.d("DEBUG","Connecting to viewholder: "+viewHolder.toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("DEBUG","Binding to view "+viewHolder.toString()+" At pos "+position);
        switch (viewHolder.getItemViewType()) {
            case NORMAL:
                SimpleTweetViews twHolder = (SimpleTweetViews) viewHolder;
                configureSimpleTweetViews(twHolder, position);
                break;
            case PHOTO:
                PhotoTweetViews ivHolder = (PhotoTweetViews) viewHolder;
                configurePhotoTweetViews(ivHolder, position);
                break;
            default:
                twHolder = (SimpleTweetViews) viewHolder;
                configureSimpleTweetViews(twHolder, position);
                break;
        }
        Log.d("DEBUG","DONE Binding to view "+viewHolder.toString()+" At pos "+position);

    }

    private void configureSimpleTweetViews(SimpleTweetViews view, int position) {
        final Tweet tweet = tweets.get(position);
        // Set view values
        view.setTweetValues(
                tweet.getUser().getScreenName(),
                tweet.getUser().getName(),
                tweet.getBody(),
                tweet.getCreatedAt(),
                tweet.gettFavoriteCount(),
                tweet.gettRetweetCount(),
                tweet.getUser().getProfileImageUrl()
                );
    }

    private void configurePhotoTweetViews(PhotoTweetViews view, int position) {
        final Tweet tweet = tweets.get(position);
        // Set view values
        view.setTweetValues(
                tweet.getUser().getScreenName(),
                tweet.getUser().getName(),
                tweet.getBody(),
                tweet.getCreatedAt(),
                tweet.gettFavoriteCount(),
                tweet.gettRetweetCount(),
                tweet.getUser().getProfileImageUrl(),
                tweet.gettMediaprofileImageUrl()
        );
    }

} // end ComplexRecyclerAdapter
