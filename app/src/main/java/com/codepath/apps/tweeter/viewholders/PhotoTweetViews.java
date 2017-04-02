package com.codepath.apps.tweeter.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.activities.OtherUserProfileActivity;
import com.codepath.apps.tweeter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.apps.tweeter.R.drawable.my_placeholder;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class PhotoTweetViews extends RecyclerView.ViewHolder implements View.OnClickListener
{
    @Bind(R.id.tvRetweetCount) public TextView tvRetweetCount;
    @Bind(R.id.tvScreenName) public TextView tvUserName;
    @Bind(R.id.tvName) public TextView tvRealName;
    @Bind(R.id.tvBody) public TextView tvBody;
    @Bind(R.id.tvTimeStamp) public TextView tvRelTime;
    @Bind(R.id.tvStarCount) public TextView tvStarCount;
    @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
    @Bind(R.id.ivPhoto) public ImageView tweetPhoto;

    List<Tweet> tweets;
    Context mContext;
    // construct
    public PhotoTweetViews(Context context, View view, List<Tweet> mTweets) {
        super(view);
        this.tweets = mTweets;
        this.mContext = context;
        view.setOnClickListener(this);
        ButterKnife.bind(this, view);
    }

    // if clicked do this
    @Override
    public void onClick(View view) {
    }

    public void setTweetValues(final String ScreenName, final String RealName, String Body,
                               String createdAt, final String favoriteCount, final String friendsCt,
                               final String reTweetCount, final String ProfileImageUrl,
                               final String tagLine, String mediaProfileImage) {
        tvUserName.setText(ScreenName);
        tvUserName.setTextColor(Color.BLACK);
        tvUserName.setTypeface(null, Typeface.BOLD);
        final String rName = "@"+RealName;
        tvRealName.setText(rName);
        tvRealName.setTextColor(Color.BLACK);
        tvBody.setText(Body);
        tvBody.setTextColor(Color.BLACK);
        String tweetTime = createdAt;
        String showReltime = getRelativeTimeAgo(tweetTime);
        tvRelTime.setText(showReltime);
        tvRelTime.setTextColor(Color.BLACK);
        tvStarCount.setText(favoriteCount);
        this.tvRetweetCount.setText(reTweetCount);
        ivProfileImage.setImageResource((android.R.color.transparent));
        Picasso.with(getContext()).load(ProfileImageUrl)
                .fit()
                .centerInside()
                .transform(new RoundedCornersTransformation(10, 10)).into(ivProfileImage);
        Picasso.with(getContext()).load(mediaProfileImage)
                .fit()
                .centerInside()
                .placeholder(my_placeholder)
                .error(my_placeholder)
                .transform(new RoundedCornersTransformation(10, 10))
                .into(tweetPhoto);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                Bundle otherUser = new Bundle();
                otherUser.putString("screen_name",ScreenName);
                otherUser.putString("Name",rName);
                otherUser.putString("followersCt",favoriteCount);
                otherUser.putString("friendsCt",friendsCt);
                otherUser.putString("tagLine",tagLine);
                otherUser.putString("ProfileImageUrl",ProfileImageUrl);
                Intent i = new Intent(mContext.getApplicationContext(),OtherUserProfileActivity.class);
                i.putExtras(otherUser);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf;
        sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        String relativeDate = "";
        long dateMillis = 0;
        try {
            dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        relativeDate = relativeDate.replace("second ago","s");
        relativeDate = relativeDate.replace("seconds ago","s");
        relativeDate = relativeDate.replace("minute ago","m");
        relativeDate = relativeDate.replace("minutes ago","m");
        relativeDate = relativeDate.replace("hour ago","h");
        relativeDate = relativeDate.replace("hours ago","h");
        relativeDate = relativeDate.replace("day ago","d");
        relativeDate = relativeDate.replace("days ago","d");
        return relativeDate;
    }
}



