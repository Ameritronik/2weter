package com.codepath.apps.tweeter.viewholders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.R;
import com.codepath.apps.tweeter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class SimpleTweetViews extends RecyclerView.ViewHolder implements View.OnClickListener {

    @Bind(R.id.tvRetweetCount) public TextView tvRetweetCount;
    @Bind(R.id.tvUserName) public TextView tvUserName;
    @Bind(R.id.tvName) public TextView tvRealName;
    @Bind(R.id.tvBody) public TextView tvBody;
    @Bind(R.id.tvTimeStamp) public TextView tvRelTime;
    @Bind(R.id.tvStarCount) public TextView tvStarCount;
    @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;

        List<Tweet> tweets;
        static Context mContext;
        // Construct
        public SimpleTweetViews(Context context, View view, List<Tweet> mTweets) {
            super(view);
            this.tweets = mTweets;
            this.mContext = context;
            view.setOnClickListener(this);
            ButterKnife.bind(this, view);
        }

        // If a click on view
        @Override
        public void onClick(View view) {

        }
        // send the article view back to requester
        //public TextView getTvArticleText() {
        //    return tvArticleText;
        //}

    public void setTweetValues(String ScreenName, String RealName, String Body,
                               String createdAt, String favoriteCount,
                               String reTweetCount, String ProfileImageUrl) {
        tvUserName.setText(ScreenName);
        tvUserName.setTextColor(Color.BLACK);
        tvUserName.setTypeface(null, Typeface.BOLD);
        String rName = "@"+RealName;
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
                .transform(new RoundedCornersTransformation(3, 3))
                .into(ivProfileImage);
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

        return relativeDate;
    }

}

