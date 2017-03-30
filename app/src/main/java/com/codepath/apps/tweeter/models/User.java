package com.codepath.apps.tweeter.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hkanekal on 3/20/2017.
 */

public class User {
    // list the attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String profile_image_url_https;
    // Getters

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        this.profile_image_url_https = profileImageUrl;
    }

    public void setProfile_image_url_https(String profile_image_url_https) {
        this.profile_image_url_https = profile_image_url_https;
    }

    // deserialize the user from json object
    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id_str");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url_https");//.replace("_normal","");
            //Log.d("DEBUG","User "+u.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return a user
        return u;
    }
}
