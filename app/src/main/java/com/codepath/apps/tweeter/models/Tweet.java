package com.codepath.apps.tweeter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** json data
 {
 "coordinates": null,
 "truncated": false,
 "created_at": "Tue Aug 28 21:16:23 +0000 2012",
 "favorited": false,
 "id_str": "240558470661799936",
 "in_reply_to_user_id_str": null,
 "entities": {
 "urls": [
 ],
 "hashtags": [
 ],
 "user_mentions": [
 ]
 },
 "text": "just another test",
 "contributors": null,
 "id": 240558470661799936,
 "retweet_count": 0,
 "in_reply_to_status_id_str": null,
 "geo": null,
 "retweeted": false,
 "in_reply_to_user_id": null,
 "place": null,
 "source": "OAuth Dancer Reborn",
 "user": {
 "name": "OAuth Dancer",
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_background_tile": true,
 "profile_sidebar_border_color": "C0DEED",
 "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "id_str": "119476949",
 "is_translator": false,
 "profile_link_color": "0084B4",
 "entities": {
 "url": {
 "urls": [
 {
 "expanded_url": null,
 "url": "http://bit.ly/oauth-dancer",
 "indices": [
 0,
 26
 ],
 "display_url": null
 }
 ]
 },
 "description": null
 },
 "default_profile": false,
 "url": "http://bit.ly/oauth-dancer",
 "contributors_enabled": false,
 "favourites_count": 7,
 "utc_offset": null,
 "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "id": 119476949,
 "listed_count": 1,
 "profile_use_background_image": true,
 "profile_text_color": "333333",
 "followers_count": 28,
 "lang": "en",
 "protected": false,
 "geo_enabled": true,
 "notifications": false,
 "description": "",
 "profile_background_color": "C0DEED",
 "verified": false,
 "time_zone": null,
 "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 "statuses_count": 166,
 "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 "default_profile_image": false,
 "friends_count": 14,
 "following": false,
 "show_all_inline_media": false,
 "screen_name": "oauth_dancer"
 },
 "in_reply_to_screen_name": null,
 "in_reply_to_status_id": null
 }
 */

// parse JSON, store data, encapsulate state logic OR display logic
public class Tweet {
    // List out the attributes
    private String body;
    private long uid; // unique id
    private com.codepath.apps.tweeter.models.User user;
    private String createdAt;
    private String tMediaType;
    private String tMediaURL;
    private Long tMediaId;
    private String tRetweetCount;
    private String tFavoriteCount;

    private String tMediaprofileImageUrl;

    public com.codepath.apps.tweeter.models.User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String gettMediaType() {
        return tMediaType;
    }

    public String gettMediaURL() {
        return tMediaURL;
    }

    public Long gettMediaId() {
        return tMediaId;
    }

    public String gettMediaprofileImageUrl() {
        return tMediaprofileImageUrl;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(com.codepath.apps.tweeter.models.User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void settMediaType(String tMediaType) {
        this.tMediaType = tMediaType;
    }

    public void settMediaURL(String tMediaURL) {
        this.tMediaURL = tMediaURL;
    }

    public void settMediaId(Long tMediaId) {
        this.tMediaId = tMediaId;
    }

    public void settMediaprofileImageUrl(String tMediaprofileImageUrl) {
        this.tMediaprofileImageUrl = tMediaprofileImageUrl;
    }

    public String gettRetweetCount() {
        return tRetweetCount;
    }

    public String gettFavoriteCount() {
        return tFavoriteCount;
    }

    // DeSerialize the JSON and build tweet objs.
    // Tweet.fromJSON ... etc

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.tRetweetCount = String.valueOf(jsonObject.getInt("retweet_count"));
            tweet.tFavoriteCount = String.valueOf(jsonObject.getInt("favorite_count"));
            tweet.user = com.codepath.apps.tweeter.models.User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return the tweet
        return tweet;
    }


    public static ArrayList<Tweet> fromJSONArray(JSONArray  jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // pick up individual tweets inside JSON and create array list of tweets
        for(int i = 0;i<jsonArray.length();i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                //Log.d("DEBUG","TWEET: "+jsonArray.getJSONObject(i).toString());
                Tweet tweet = Tweet.fromJSON(tweetJson);
                JSONObject entities = tweetJson.getJSONObject("entities");
                if(entities.has("media"))
                {   JSONArray mMediaArray = entities.getJSONArray("media");
                    //Log.d("DEBUG","MediaArray: "+mMediaArray.toString());
                    JSONObject inJson = mMediaArray.getJSONObject(0);
                    tweet.tMediaType = inJson.getString("type");
                    tweet.tMediaprofileImageUrl = inJson.getString("media_url_https");
                    tweet.tMediaId = inJson.getLong("id");
                    //Log.d("DEBUG", "MediaType: " + tweet.tMediaType);
                } else {
                    tweet.tMediaType = null;
                    tweet.tMediaprofileImageUrl = "";
                    tweet.tMediaId = Long.valueOf(1);
                }
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }


}
